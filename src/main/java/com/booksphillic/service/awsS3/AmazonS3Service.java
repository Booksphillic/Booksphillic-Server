package com.booksphillic.service.awsS3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AmazonS3Service implements FileService {

    private final AmazonS3 amazonS3;
    private final AmazonS3Component amazonS3Component;

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata,
                           String fileName) {
        amazonS3.putObject(new PutObjectRequest(amazonS3Component.getBucket(), fileName,
                inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicReadWrite));
    }

    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(amazonS3Component.getBucket(), fileName).toString();
    }
}
