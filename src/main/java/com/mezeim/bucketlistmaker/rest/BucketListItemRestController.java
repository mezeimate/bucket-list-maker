package com.mezeim.bucketlistmaker.rest;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import com.google.firebase.auth.FirebaseAuthException;
import com.mezeim.bucketlistmaker.common.BucketListPath;
import com.mezeim.bucketlistmaker.dto.CreateBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.DeleteBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.GetBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.JoinBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.ModifyBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.dto.QueryBucketListRequestDTO;
import com.mezeim.bucketlistmaker.service.BucketListItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Tag(name = "BucketListItem interfaces")
@RequestMapping(produces = BucketListPath.BASE_PATH)
public class BucketListItemRestController {

    @Autowired
    private BucketListItemService bucketListItemService;

    @ResponseBody
    @PostMapping(BucketListPath.POST_BUCKET)
    ResponseEntity<Object> createBucketListItem(@Valid @RequestBody CreateBucketListItemRequestDTO createBucketListItemRequestDTO)
            throws ExecutionException, FirebaseAuthException, InterruptedException {
        return bucketListItemService.createBucketListItem(createBucketListItemRequestDTO);
    }

    @ResponseBody
    @PostMapping(BucketListPath.JOIN_BUCKET)
    ResponseEntity<Object> joinBucketListItem(@Valid @RequestBody JoinBucketListItemRequestDTO joinBucketListItemRequestDTO)
            throws ExecutionException, InterruptedException {
        return bucketListItemService.joinBucket(joinBucketListItemRequestDTO);
    }

    @ResponseBody
    @PostMapping(BucketListPath.QUERY_BUCKET)
    ResponseEntity<Object> queryBucketList(@Valid @RequestBody QueryBucketListRequestDTO queryBucketListRequestDTO)
            throws ExecutionException, InterruptedException {
        return bucketListItemService.queryBucketListItems(queryBucketListRequestDTO);
    }

    @ResponseBody
    @DeleteMapping(BucketListPath.DELETE_BUCKET)
    ResponseEntity<Object> deleteBucketListItem(@Valid @RequestBody DeleteBucketListItemRequestDTO request) {
        return bucketListItemService.deleteBucketListItem(request);
    }

    @ResponseBody
    @PutMapping(BucketListPath.MODIFY_BUCKET)
    ResponseEntity<Object> modifyBucketListItem(@PathVariable String id, @Valid @RequestBody ModifyBucketListItemRequestDTO requestDTO)
            throws ExecutionException, InterruptedException {
        return bucketListItemService.modifyBucketListItem(id, requestDTO);
    }

    @ResponseBody
    @PostMapping(BucketListPath.MODIFY_BUCKET)
    ResponseEntity<Object> getBucketListItem(@PathVariable String id, @Valid @RequestBody GetBucketListItemRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        return bucketListItemService.getBucketListItem(id, requestDTO);
    }
}
