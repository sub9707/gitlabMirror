package com.repomon.rocketdan.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Utils {
    @Value("${s3.prefix}")
    private static String s3Prefix;

    public static String getS3Url(String fileName){
        return s3Prefix + fileName;
    }
}
