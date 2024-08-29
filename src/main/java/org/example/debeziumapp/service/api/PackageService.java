package org.example.debeziumapp.service.api;

import org.example.debeziumapp.dto.PackageDto;

public interface PackageService {

    byte[] unloadPackage(Long packageId);

    void deletePackage(Long packageId);

    void createPackage(PackageDto packageDto);
}
