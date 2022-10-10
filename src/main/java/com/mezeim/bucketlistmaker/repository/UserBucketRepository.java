package com.mezeim.bucketlistmaker.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mezeim.bucketlistmaker.common.AppConstants;
import com.mezeim.bucketlistmaker.entity.UserBucketListItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class UserBucketRepository {

    public void save(UserBucketListItem userBucketListItem) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = firestore.collection(AppConstants.USER_BUCKET_DOCUMENT).document();
        addedDocRef.set(userBucketListItem);
        userBucketListItem.setDocumentId(addedDocRef.getId());
    }

    public List<String> getOwnBucketListItemIds(String uid) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference userBuckets = firestore.collection(AppConstants.USER_BUCKET_DOCUMENT);

        Query query = userBuckets.whereEqualTo(AppConstants.USER_ID, uid);
        List<QueryDocumentSnapshot> documents = getSnapshots(query);

        List<String> bucketIds = new ArrayList<>();
        documents.forEach(d -> bucketIds.add(d.toObject(UserBucketListItem.class).getBucketId()));
        return bucketIds;
    }

    private List<QueryDocumentSnapshot> getSnapshots(Query query) throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> apiFuture = query.get();
        return apiFuture.get().getDocuments();
    }

}
