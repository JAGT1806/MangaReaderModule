package com.jagt.reader.user.domain.port.output;

import java.time.Duration;

public interface FileStorageService {
    String uploadFile(byte[] fileContent, String fileName, String contentType);
    void deleteFile(String fileName);
    boolean fileExists(String fileName);
    String generarePreSignedDownloadUrl(String fileName, Duration duration);
}
