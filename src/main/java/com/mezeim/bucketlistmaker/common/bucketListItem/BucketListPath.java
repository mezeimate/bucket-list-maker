package com.mezeim.bucketlistmaker.common.bucketListItem;

public class BucketListPath {

    public final static String BASE_PATH = "application/json";
    public final static String BUCKET_REST = "/bucket";
    public static final String POST_BUCKET = BUCKET_REST + "/create";
    public static final String JOIN_BUCKET = BUCKET_REST + "/join";
    public static final String QUERY_BUCKET = BUCKET_REST + "/query";
    public static final String ID = "/{id}";
    public static final String DELETE_BUCKET = BUCKET_REST + "/delete" + ID;
    public static final String MODIFY_BUCKET = BUCKET_REST + "/modify" + ID;
    public static final String GET_BUCKET_LIST_ITEM = BUCKET_REST + "/get" + ID;
}
