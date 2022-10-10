package com.mezeim.bucketlistmaker.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBucketListItem {

    @DocumentId
    private String documentId;

    private String userId;

    private String bucketId;

}
