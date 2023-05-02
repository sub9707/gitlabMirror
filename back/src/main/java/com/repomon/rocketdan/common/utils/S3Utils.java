package com.repomon.rocketdan.common.utils;

public class S3Utils {
    private static final String s3Prefix = "https://repomon.s3.ap-northeast-2.amazonaws.com/models/";

    public static String getS3Url(String fileName){
        return s3Prefix + fileName;
    }
}
