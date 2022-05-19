package com.booksphillic.service.bookstore;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {

    private final FileService amazonS3Service;

    public String uploadImage(MultipartFile file, String fileFolder) {
        String fileName = fileFolder + "/" + createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러 발생") + file.getOriginalFilename());
        }
        return amazonS3Service.getFileUrl(fileName);
    }
    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFilename)); //여기 규칙에 맞게 수정!
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
