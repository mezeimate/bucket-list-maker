package com.mezeim.bucketlistmaker.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.mezeim.bucketlistmaker.common.AppConstants;
import com.mezeim.bucketlistmaker.entity.UserBucketListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class UserBucketRepository {

    @Autowired
    private Firestore firestore;

    public void save(UserBucketListItem userBucketListItem) {
        DocumentReference addedDocRef = firestore.collection(AppConstants.USER_BUCKET_DOCUMENT).document();
        addedDocRef.set(userBucketListItem);
        userBucketListItem.setDocumentId(addedDocRef.getId());
    }

    public List<String> getOwnBucketListItemIds(String uid) throws ExecutionException, InterruptedException {
        CollectionReference userBuckets = firestore.collection(AppConstants.USER_BUCKET_DOCUMENT);

        Query query = userBuckets.whereEqualTo(AppConstants.USER_ID, uid);
        List<QueryDocumentSnapshot> documents = getSnapshots(query);

        List<String> bucketIds = new ArrayList<>();
        documents.forEach(d -> bucketIds.add(d.toObject(UserBucketListItem.class).getBucketId()));
        return bucketIds;
    }

    public List<String> getUserNamesByBucketListItem(String bucketListItemId) throws ExecutionException, InterruptedException {
        CollectionReference userBuckets = firestore.collection(AppConstants.USER_BUCKET_DOCUMENT);

        Query query = userBuckets.whereEqualTo(AppConstants.BUCKET_ID, bucketListItemId);
        List<QueryDocumentSnapshot> documents = getSnapshots(query);

        List<String> userNames = new ArrayList<>();
        documents.forEach(d -> {
            String userId = d.toObject(UserBucketListItem.class).getUserId();
            userNames.add(getUsername(userId));
        });

        return userNames;
    }

    private String getUsername(String uid) {
        String username;
        try {
            username = FirebaseAuth.getInstance().getUser(uid).getDisplayName();
        } catch (Exception e) {
            username = "";
        }
        return username;
    }

    private List<QueryDocumentSnapshot> getSnapshots(Query query) throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> apiFuture = query.get();
        return apiFuture.get().getDocuments();
    }

}
