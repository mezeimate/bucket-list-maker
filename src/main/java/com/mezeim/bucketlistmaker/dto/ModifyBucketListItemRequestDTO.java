package com.mezeim.bucketlistmaker.dto;

import com.mezeim.bucketlistmaker.common.dto.AbstractIdentifierDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ModifyBucketListItemRequestDTO extends AbstractIdentifierDTO {

    @Size(max = 50)
    @NotNull
    private String title;

    @Size(max = 200)
    private String description;

    private boolean ready;
}
