package org.example.debeziumapp.service.api;

import org.example.debeziumapp.dto.PackageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PackageService {

    byte[] unloadPackage(PackageDto packagesDto);
}
