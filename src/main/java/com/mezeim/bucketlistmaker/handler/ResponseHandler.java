package com.mezeim.bucketlistmaker.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object responseObj) {
        Map<String, Object> map = getStringObjectMap(status);
        map.put("data", responseObj);
        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status) {
        Map<String, Object> map = getStringObjectMap(status);
        return new ResponseEntity<>(map,status);
    }

    private static Map<String, Object> getStringObjectMap(HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("functionCode", status.getReasonPhrase());
        map.put("status", status.value());
        return map;
    }
}
