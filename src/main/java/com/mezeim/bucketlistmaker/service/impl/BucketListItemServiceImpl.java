package com.mezeim.bucketlistmaker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.mezeim.bucketlistmaker.common.AuthorizedUtil;
import com.mezeim.bucketlistmaker.converter.QueryBucketListResponseConverter;
import com.mezeim.bucketlistmaker.dto.CreateBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.DeleteBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.GetBucketListItemRequestDTO;
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
    public ResponseEntity<BucketListItem> createBucketListItem(String idToken, CreateBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        String userId = AuthorizedUtil.getUserId(idToken);

        ModelMapper modelMapper = new ModelMapper();
        BucketListItem bucket = modelMapper.map(requestDTO, BucketListItem.class);
        bucket.setComplete(false);
        bucket.setInviteCode(UUID.randomUUID().toString());
        BucketListItem savedBucketListItem = bucketListItemRepository.save(bucket);

        userBucketListItemMatch(userId, savedBucketListItem.getDocumentId());
        log.info("Created {}", bucket);

        return ResponseEntity.status(HttpStatus.OK).body(bucket);
    }

    @Override
    public ResponseEntity<Object> joinBucket(String idToken, JoinBucketListItemRequestDTO joinBucketListItemRequestDTO) throws ExecutionException, InterruptedException {
        String userId = AuthorizedUtil.getUserId(idToken);
        String inviteCode = joinBucketListItemRequestDTO.getInviteCode();
        String bucketId = bucketListItemRepository.findBucketListItemIdByInviteCode(inviteCode);

        userBucketListItemMatch(userId, bucketId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QueryBucketListResponseDTO>> queryBucketListItems(String idToken, QueryBucketListRequestDTO queryRequestDTO) throws ExecutionException, InterruptedException {
        String userId = AuthorizedUtil.getUserId(idToken);
        List<String> ownBucketIds = userBucketRepository.getOwnBucketListItemIds(userId);
        List<BucketListItem> bucketList = bucketListItemRepository.queryBucketListItems(ownBucketIds);

        log.info("Query items by [{}] user.", userId);
        List<QueryBucketListResponseDTO> response = new ArrayList<>();
        for (BucketListItem item : bucketList) {
            log.info(item.toString());
            response.add(bucketListResponseConverter.convert(item));
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Object> deleteBucketListItem(String id, String idToken) {
        AuthorizedUtil.getUserId(idToken);
        bucketListItemRepository.delete(id);
        log.info("Deleted [{}] bucketListItem.", id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BucketListItem> modifyBucketListItem(String id, String idToken, ModifyBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        AuthorizedUtil.getUserId(idToken);
        return ResponseHandler.generateResponse(HttpStatus.OK, bucketListItemRepository.modify(requestDTO, id));
    }

    @Override
    public ResponseEntity<BucketListItem> getBucketListItem(String id, String sessionIdToken) throws ExecutionException, InterruptedException {
        AuthorizedUtil.getUserId(sessionIdToken);
        BucketListItem bucketListItem = bucketListItemRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bucketListItem);
    }

    private void userBucketListItemMatch(String userId, String bucketId) throws ExecutionException, InterruptedException {
        if (!bucketListItemRepository.hasBucketListItem(bucketId, userId)) {
            UserBucketListItem userBucketListItem = new UserBucketListItem();
            userBucketListItem.setBucketId(bucketId);
            userBucketListItem.setUserId(userId);
            userBucketRepository.save(userBucketListItem);
            log.info("Created BucketListItem [{}] - User [{}] join.", bucketId, userId);
        }
    }
}
