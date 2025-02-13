package com.app.treen.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {

    private final S3Client amazonS3;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;  // S3 리전 값

    private String defaultImageUrl = "default.url";

    // 단일 파일 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            log.warn("파일이 제공되지 않음. 기본 URL 반환");
            return defaultImageUrl; // 파일이 없으면 기본 이미지 반환
        }

        File uploadFile = convert(multipartFile);
        return upload(uploadFile, dirName);
    }


    // 다중 파일 업로드
    public List<String> upload(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            fileUrls.add(upload(file, dirName)); // 개별적으로 업로드 수행
        }
        return fileUrls;
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + uploadFile.getName(); // 파일명에 UUID 추가
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        amazonS3.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(uploadFile.getPath())));
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
    }

    public void deleteImage(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains(bucket)) {
            log.warn("잘못된 이미지 URL: {}", imageUrl);
            return;
        }

        // S3에서 삭제할 Key 추출
        String fileKey = imageUrl.substring(imageUrl.indexOf(bucket) + bucket.length() + 1); // 버킷 경로 이후 문자열 추출

        amazonS3.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(fileKey)
                .build());

        log.info("이미지 삭제 완료: {}", imageUrl);
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info(targetFile.getName() + " 파일 삭제 완료");
        } else {
            log.warn(targetFile.getName() + " 파일 삭제 실패");
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IOException("파일이 제공되지 않았습니다.");
        }

        File convertFile = File.createTempFile(UUID.randomUUID().toString(), "_" + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convertFile;
    }

}