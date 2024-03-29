package com.mezeim.bucketlistmaker.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class AbstractIdentifierDTO {

    @NotNull
    private String idToken;

}
