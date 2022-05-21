package com.booksphillic.service.awsS3;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AmazonS3Component {

    @Value("${cloud.aws.s3.bucket}") //버킷명
    private String bucket;

    @Value("${cloud.aws.s3.bucket}") // 폴더명
    private String folderName;
}
