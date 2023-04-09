package com.mezeim.bucketlistmaker.converter;

import java.util.concurrent.ExecutionException;

import com.mezeim.bucketlistmaker.dto.QueryBucketListResponseDTO;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import com.mezeim.bucketlistmaker.repository.UserBucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryBucketListResponseConverter {

    @Autowired
    private UserBucketRepository userBucketRepository;

    public QueryBucketListResponseDTO convert(BucketListItem bucketListItem) throws ExecutionException, InterruptedException {
        QueryBucketListResponseDTO responseDTO = new QueryBucketListResponseDTO();
        responseDTO.setDocumentId(bucketListItem.getDocumentId());
        responseDTO.setMembers(userBucketRepository.getUserNamesByBucketListItem(bucketListItem.getDocumentId()));
        responseDTO.setComplete(bucketListItem.isComplete());
        responseDTO.setTitle(bucketListItem.getTitle());
        return responseDTO;
    }
}
