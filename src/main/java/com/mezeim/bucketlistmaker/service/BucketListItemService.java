package com.mezeim.bucketlistmaker.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mezeim.bucketlistmaker.dto.*;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public interface BucketListItemService {

    ResponseEntity<BucketListItem> createBucketListItem(String idToken, CreateBucketListItemRequestDTO createBucketListItemRequestDTO) throws FirebaseAuthException, ExecutionException, InterruptedException;

    ResponseEntity<Object> joinBucket(String idToken, JoinBucketListItemRequestDTO joinBucketListItemRequestDTO) throws ExecutionException, InterruptedException;

    ResponseEntity<List<QueryBucketListResponseDTO>> queryBucketListItems(String idToken, QueryBucketListRequestDTO queryRequestDTO) throws ExecutionException, InterruptedException;

    ResponseEntity<Object> deleteBucketListItem(String id, String idToken);

    ResponseEntity<BucketListItem> modifyBucketListItem(String id, String idToken, ModifyBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException;

    ResponseEntity<BucketListItem> getBucketListItem(String id, String requestDTO) throws ExecutionException, InterruptedException;

}
