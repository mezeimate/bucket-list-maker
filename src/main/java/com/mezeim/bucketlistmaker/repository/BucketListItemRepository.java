package com.mezeim.bucketlistmaker.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.mezeim.bucketlistmaker.common.AppConstants;
import com.mezeim.bucketlistmaker.dto.ModifyBucketListItemRequestDTO;
import com.mezeim.bucketlistmaker.entity.BucketListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class BucketListItemRepository {

    @Autowired
    private Firestore firestore;

    public BucketListItem save(BucketListItem bucket) {
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

    public BucketListItem findById(String id) throws ExecutionException, InterruptedException {
        CollectionReference buckets = getBucketCollectionReference();
        Query query = buckets.whereEqualTo(FieldPath.documentId(), id);
        List<QueryDocumentSnapshot> documents = getSnapshots(query);
        return documents.get(0).toObject(BucketListItem.class);
    }

    public boolean hasBucketListItem(String itemId, String userId) throws ExecutionException, InterruptedException {
        CollectionReference buckets = getBucketUserCollectionReference();
        Query query = buckets.whereEqualTo(AppConstants.BUCKET_ID, itemId).whereEqualTo(AppConstants.USER_ID, userId);
        List<QueryDocumentSnapshot> documents = getSnapshots(query);
        return !documents.isEmpty();
    }

    public List<BucketListItem> queryBucketListItems(List<String> ownBucketIds) throws ExecutionException, InterruptedException {
        CollectionReference buckets = firestore.collection(AppConstants.BUCKET_DOCUMENT);
        ApiFuture<QuerySnapshot> apiFuture = buckets.get();
        QuerySnapshot queryDocumentSnapshots = apiFuture.get();
        List<QueryDocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

        return documents.stream().map(d -> d.toObject(BucketListItem.class))
                .filter(b -> ownBucketIds.contains(b.getDocumentId())).toList();
    }

    public void delete(String bucketListItemId) {
        firestore.collection(AppConstants.BUCKET_DOCUMENT).document(bucketListItemId).delete();
    }

    public DocumentReference modify(String id, ModifyBucketListItemRequestDTO requestDTO) {
        DocumentReference addedDocRef = firestore.collection(AppConstants.BUCKET_DOCUMENT).document(id);
        addedDocRef.update(AppConstants.TITLE, requestDTO.getTitle());
        addedDocRef.update(AppConstants.DESCRIPTION, requestDTO.getDescription());
        addedDocRef.update(AppConstants.COMPLETE, requestDTO.isComplete());
        return addedDocRef;
    }

    private List<QueryDocumentSnapshot> getSnapshots(Query query) throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> apiFuture = query.get();
        return apiFuture.get().getDocuments();
    }

    private CollectionReference getBucketCollectionReference() {
        return firestore.collection(AppConstants.BUCKET_DOCUMENT);
    }

    private CollectionReference getBucketUserCollectionReference() {
        return firestore.collection(AppConstants.USER_BUCKET_DOCUMENT);
    }
}
