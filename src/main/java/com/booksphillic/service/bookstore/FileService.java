package com.booksphillic.service.bookstore;

import java.io.InputStream;
import com.amazonaws.services.s3.model.ObjectMetadata;

public interface FileService { //AWS S3 외의 다른 클라우드 서비스 사용시 쉽게 교체하기 위한 파일 처리 인터페이스

    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);
}
