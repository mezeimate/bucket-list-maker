package com.mezeim.bucketlistmaker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.mezeim.bucketlistmaker.common.AuthorizedUtil;
import com.mezeim.bucketlistmaker.converter.QueryBucketListResponseConverter;
import com.mezeim.bucketlistmaker.dto.CreateBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.DeleteBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.JoinBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.ModifyBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.QueryBucketListRequestDTO;
import com.mezeim.bucketlistmaker.dto.QueryBucketListResponseDTO;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import com.mezeim.bucketlistmaker.entity.UserBucketListItem;
import com.mezeim.bucketlistmaker.handler.ResponseHandler;
import com.mezeim.bucketlistmaker.repository.BucketListItemRepository;
import com.mezeim.bucketlistmaker.repository.UserBucketRepository;
import com.mezeim.bucketlistmaker.service.BucketListItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BucketListItemServiceImpl implements BucketListItemService {

    @Autowired
    private BucketListItemRepository bucketListItemRepository;

    @Autowired
    private UserBucketRepository userBucketRepository;

    @Autowired
    private QueryBucketListResponseConverter bucketListResponseConverter;

    @Override
    public ResponseEntity<Object> createBucketListItem(CreateBucketListItemRequestDTO requestDTO) {
        String userId = AuthorizedUtil.getUserId(requestDTO.getIdToken());

        ModelMapper modelMapper = new ModelMapper();
        BucketListItem bucket = modelMapper.map(requestDTO, BucketListItem.class);
        bucket.setReady(false);
        bucket.setInviteCode(UUID.randomUUID().toString());
        BucketListItem savedBucketListItem = bucketListItemRepository.save(bucket);

        UserBucketListItem userBucketListItem = userBucketListItemMatch(userId, savedBucketListItem.getDocumentId());
        userBucketRepository.save(userBucketListItem);
        return ResponseHandler.generateResponse(HttpStatus.OK, bucket);
    }

    @Override
    public ResponseEntity<Object> joinBucket(JoinBucketListItemRequestDTO joinBucketListItemRequestDTO) throws ExecutionException, InterruptedException {
        String idToken = joinBucketListItemRequestDTO.getIdToken();
        String userId = AuthorizedUtil.getUserId(idToken);
        String inviteCode = joinBucketListItemRequestDTO.getInviteCode();
        String bucketId = bucketListItemRepository.findBucketListItemIdByInviteCode(inviteCode);

        UserBucketListItem userBucketListItem = userBucketListItemMatch(userId, bucketId);
        userBucketRepository.save(userBucketListItem);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> queryBucketListItems(QueryBucketListRequestDTO queryRequestDTO) throws ExecutionException, InterruptedException {
        String idToken = queryRequestDTO.getIdToken();
        String userId = AuthorizedUtil.getUserId(idToken);
        List<String> ownBucketIds = userBucketRepository.getOwnBucketListItemIds(userId);
        List<BucketListItem> bucketList = bucketListItemRepository.queryBucketListItems(ownBucketIds);

        List<QueryBucketListResponseDTO> response = new ArrayList<>();
        for (BucketListItem item : bucketList) {
            response.add(bucketListResponseConverter.convert(item));
        }

        return ResponseHandler.generateResponse(HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<Object> deleteBucketListItem(DeleteBucketListItemRequestDTO request) {
        AuthorizedUtil.getUserId(request.getIdToken());
        bucketListItemRepository.delete(request.getBucketListItemId());
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> modifyBucketListItem(String id, ModifyBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = bucketListItemRepository.modify(requestDTO, id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        BucketListItem item = documentSnapshot.toObject(BucketListItem.class);
        return ResponseHandler.generateResponse(HttpStatus.OK, item);
    }

    @Override
    public ResponseEntity<Object> getBucketListItem(String id) {
        return null;
    }

    private UserBucketListItem userBucketListItemMatch(String userId, String bucketId) {
        //TODO ha még nem létezik ilyen kapcsolat csak akkor hajtódjon végre
        UserBucketListItem userBucketListItem = new UserBucketListItem();
        userBucketListItem.setBucketId(bucketId);
        userBucketListItem.setUserId(userId);
        return userBucketListItem;
    }

}
