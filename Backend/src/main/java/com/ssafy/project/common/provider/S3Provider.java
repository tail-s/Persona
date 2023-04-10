package com.ssafy.project.common.provider;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface S3Provider {

    String uploadFile(File file, String uri);
    String uploadMultipartFile(MultipartFile file, String uri);
    void delete(String uri);
}
