package com.mezeim.bucketlistmaker.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.mezeim.bucketlistmaker.common.AppConstants;
import com.mezeim.bucketlistmaker.common.bucketListItem.BucketListPath;
import com.mezeim.bucketlistmaker.dto.*;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import com.mezeim.bucketlistmaker.service.BucketListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping(produces = BucketListPath.BASE_PATH)
public class BucketListItemRestController {

    @Autowired
    private BucketListItemService bucketListItemService;

    @ResponseBody
    @PostMapping(value = BucketListPath.POST_BUCKET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BucketListItem> createBucketListItem(@RequestHeader(value = AppConstants.REQUEST_HEADER_TOKEN) String idToken, @Valid @RequestBody CreateBucketListItemRequestDTO createBucketListItemRequestDTO) throws ExecutionException, FirebaseAuthException, InterruptedException {
        return bucketListItemService.createBucketListItem(idToken, createBucketListItemRequestDTO);
    }

    @ResponseBody
    @PostMapping(value = BucketListPath.JOIN_BUCKET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> joinBucketListItem(@RequestHeader(value = AppConstants.REQUEST_HEADER_TOKEN) String idToken, @Valid @RequestBody JoinBucketListItemRequestDTO joinBucketListItemRequestDTO) throws ExecutionException, InterruptedException {
        return bucketListItemService.joinBucket(idToken, joinBucketListItemRequestDTO);
    }

    @ResponseBody
    @PostMapping(value = BucketListPath.QUERY_BUCKET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<QueryBucketListResponseDTO>> queryBucketList(@RequestHeader(value = AppConstants.REQUEST_HEADER_TOKEN) String idToken, @Valid @RequestBody QueryBucketListRequestDTO queryBucketListRequestDTO) throws ExecutionException, InterruptedException {
        return bucketListItemService.queryBucketListItems(idToken, queryBucketListRequestDTO);
    }

    @ResponseBody
    @DeleteMapping(value = BucketListPath.DELETE_BUCKET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> deleteBucketListItem(@PathVariable String id, @RequestHeader(value = AppConstants.REQUEST_HEADER_TOKEN) String idToken) {
        return bucketListItemService.deleteBucketListItem(id, idToken);
    }

    @ResponseBody
    @PatchMapping(value = BucketListPath.MODIFY_BUCKET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BucketListItem> modifyBucketListItem(@PathVariable String id, @RequestHeader(value = AppConstants.REQUEST_HEADER_TOKEN) String idToken, @Valid @RequestBody ModifyBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        return bucketListItemService.modifyBucketListItem(id, idToken, requestDTO);
    }

    @ResponseBody
    @GetMapping(value = BucketListPath.GET_BUCKET_LIST_ITEM, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BucketListItem> getBucketListItem(@PathVariable String id, @RequestHeader(value = AppConstants.REQUEST_HEADER_TOKEN) String idToken) throws ExecutionException, InterruptedException {
        return bucketListItemService.getBucketListItem(id, idToken);
    }
}
