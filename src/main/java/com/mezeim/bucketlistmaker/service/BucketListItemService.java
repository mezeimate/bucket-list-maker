package com.mezeim.bucketlistmaker.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mezeim.bucketlistmaker.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

public interface BucketListItemService {

    ResponseEntity<Object> createBucketListItem(CreateBucketListItemRequestDTO createBucketListItemRequestDTO) throws FirebaseAuthException, ExecutionException, InterruptedException;

    ResponseEntity<Object> joinBucket(JoinBucketListItemRequestDTO joinBucketListItemRequestDTO) throws ExecutionException, InterruptedException;

    ResponseEntity<Object> queryBucketListItems(QueryBucketListRequestDTO queryBucketListRequestDTO) throws ExecutionException, InterruptedException;

    ResponseEntity<Object> deleteBucketListItem(DeleteBucketListItemRequestDTO request);

    ResponseEntity<Object> modifyBucketListItem(String id, ModifyBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException;

}
