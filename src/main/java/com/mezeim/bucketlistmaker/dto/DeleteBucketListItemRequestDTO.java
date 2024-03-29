package com.mezeim.bucketlistmaker.dto;

import com.mezeim.bucketlistmaker.common.dto.AbstractIdentifierDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeleteBucketListItemRequestDTO extends AbstractIdentifierDTO {

    @NotNull
    private String bucketListItemId;
}
