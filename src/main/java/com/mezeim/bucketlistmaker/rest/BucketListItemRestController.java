package com.mezeim.bucketlistmaker.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.mezeim.bucketlistmaker.common.BucketListPath;
import com.mezeim.bucketlistmaker.dto.*;
import com.mezeim.bucketlistmaker.service.BucketListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@Controller
@CrossOrigin
@RequestMapping(produces = BucketListPath.BASE_PATH)
public class BucketListItemRestController {

    @Autowired
    private BucketListItemService bucketListItemService;

    @ResponseBody
    @PostMapping(BucketListPath.POST_BUCKET)
    ResponseEntity<Object> createBucket(@Valid @RequestBody CreateBucketListItemRequestDTO createBucketListItemRequestDTO)
            throws ExecutionException, FirebaseAuthException, InterruptedException {
        return bucketListItemService.createBucketListItem(createBucketListItemRequestDTO);
    }

    @ResponseBody
    @PostMapping(BucketListPath.JOIN_BUCKET)
    ResponseEntity<Object> joinBucket(@Valid @RequestBody JoinBucketListItemRequestDTO joinBucketListItemRequestDTO)
            throws ExecutionException, InterruptedException {
        return bucketListItemService.joinBucket(joinBucketListItemRequestDTO);
    }

    @ResponseBody
    @PostMapping(BucketListPath.QUERY_BUCKET)
    ResponseEntity<Object> queryBucket(@Valid @RequestBody QueryBucketListRequestDTO queryBucketListRequestDTO)
            throws ExecutionException, InterruptedException {
        return bucketListItemService.queryBucketListItems(queryBucketListRequestDTO);
    }

    @ResponseBody
    @DeleteMapping(BucketListPath.DELETE_BUCKET)
    ResponseEntity<Object> deleteBucket(@Valid @RequestBody DeleteBucketListItemRequestDTO request) {
        return bucketListItemService.deleteBucketListItem(request);
    }

    @ResponseBody
    @PutMapping(BucketListPath.MODIFY_BUCKET)
    ResponseEntity<Object> modifyBucket(@PathVariable String id, @Valid @RequestBody ModifyBucketListItemRequestDTO requestDTO)
            throws ExecutionException, InterruptedException {
        return bucketListItemService.modifyBucketListItem(id, requestDTO);
    }
}
