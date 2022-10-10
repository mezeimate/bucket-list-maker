package com.mezeim.bucketlistmaker.common.validators;

import com.mezeim.bucketlistmaker.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Validators {

    public void getEntity(Object entity){
        if(!Objects.nonNull(entity)){
            throw new NotFoundException();
        }
    }
}
