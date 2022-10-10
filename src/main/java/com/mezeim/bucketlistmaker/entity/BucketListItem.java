package com.mezeim.bucketlistmaker.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class BucketListItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @DocumentId
    private String documentId;

    private String title;

    private String description;

    private boolean ready;

    private String inviteCode;

    public BucketListItem() {
        this.inviteCode = UUID.randomUUID().toString();
        //this.inviteCode = InviteCodeGenerator.generate();
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ready=" + ready +
                ", inviteCode='" + inviteCode + '\'' +
                '}';
    }
}
