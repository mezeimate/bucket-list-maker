package com.mezeim.bucketlistmaker.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JoinBucketListItemRequestDTO {

    @NotNull
    private String inviteCode;

}
