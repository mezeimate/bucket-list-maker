package com.mezeim.bucketlistmaker.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mezeim.bucketlistmaker.common.AppConstants;
import com.mezeim.bucketlistmaker.dto.ModifyBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class BucketListItemRepository {

    public BucketListItem save(BucketListItem bucket) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = firestore.collection(AppConstants.BUCKET_DOCUMENT).document();
        addedDocRef.set(bucket);
        bucket.setDocumentId(addedDocRef.getId());
        return bucket;
    }

    public String findBucketListItemIdByInviteCode(String inviteCode) throws ExecutionException, InterruptedException {
        CollectionReference buckets = getBucketCollectionReference();
        Query query = buckets.whereEqualTo(AppConstants.INVITE_CODE, inviteCode);
        List<QueryDocumentSnapshot> documents = getSnapshots(query);
        return documents.get(0).toObject(BucketListItem.class).getDocumentId();
    }

    public List<BucketListItem> queryBucketListItems(List<String> ownBucketIds) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference buckets = firestore.collection(AppConstants.BUCKET_DOCUMENT);
        ApiFuture<QuerySnapshot> apiFuture = buckets.get();
        QuerySnapshot queryDocumentSnapshots = apiFuture.get();
        List<QueryDocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

        return documents.stream().map(d -> d.toObject(BucketListItem.class))
                .filter(b -> ownBucketIds.contains(b.getDocumentId())).toList();
    }

    private List<QueryDocumentSnapshot> getSnapshots(Query query) throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> apiFuture = query.get();
        return apiFuture.get().getDocuments();
    }

    private CollectionReference getBucketCollectionReference() {
        Firestore firestore = FirestoreClient.getFirestore();
        return firestore.collection(AppConstants.BUCKET_DOCUMENT);
    }

    public void delete(String bucketListItemId) {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(AppConstants.BUCKET_DOCUMENT).document(bucketListItemId).delete();
    }

    public DocumentReference modify(ModifyBucketListItemRequestDTO requestDTO, String id) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = firestore.collection(AppConstants.BUCKET_DOCUMENT).document(id);
        addedDocRef.update(AppConstants.TITLE, requestDTO.getTitle());
        addedDocRef.update(AppConstants.DESCRIPTION, requestDTO.getDescription());
        addedDocRef.update(AppConstants.READY, requestDTO.isReady());
        return addedDocRef;
    }
}