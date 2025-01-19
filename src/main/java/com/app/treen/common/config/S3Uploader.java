package com.app.treen.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
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

    // 단일 파일 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IOException("Failed to create a file from MultipartFile"));
        return upload(uploadFile, dirName);
    }

    // 다중 파일 업로드
    public List<String> upload(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            fileUrls.add(upload(file, dirName));
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

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info(targetFile.getName() + " 파일 삭제 완료");
        } else {
            log.warn(targetFile.getName() + " 파일 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}