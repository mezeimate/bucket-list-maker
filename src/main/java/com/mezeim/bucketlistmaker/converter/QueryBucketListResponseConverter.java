package com.mezeim.bucketlistmaker.converter;

import com.mezeim.bucketlistmaker.dto.QueryBucketListResponseDTO;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import javax.persistence.Converter;

@Service
public class QueryBucketListResponseConverter {

    public QueryBucketListResponseDTO convert(BucketListItem bucketListItem){
        QueryBucketListResponseDTO responseDTO = new QueryBucketListResponseDTO();
        responseDTO.setDocumentId(bucketListItem.getDocumentId());
        responseDTO.setMembers(null);
        responseDTO.setReady(bucketListItem.isReady());
        responseDTO.setTitle(bucketListItem.getTitle());
        return responseDTO;
    }
}
