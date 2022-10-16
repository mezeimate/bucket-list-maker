package com.mezeim.bucketlistmaker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QueryBucketListResponseDTO {

    private String documentId;

    private String title;

    private List<String> members;

    private boolean ready;

}
