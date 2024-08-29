package org.example.debeziumapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.debeziumapp.dto.PackageDto;
import org.example.debeziumapp.entity.Change;
import org.example.debeziumapp.entity.Package;
import org.example.debeziumapp.entity.enums.PackageStatus;
import org.example.debeziumapp.repository.api.ChangeRepository;
import org.example.debeziumapp.repository.api.PackageRepository;
import org.example.debeziumapp.service.api.PackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageServiceImpl implements PackageService {

    private final ChangeRepository changeRepository;

    private final PackageRepository packageRepository;

    @Override
    @Transactional
    public byte[] unloadPackage(Long packageId) {
        Package pack = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        List<Long> changeIds = pack.getChanges().stream()
                .map(Change::getId)
                .toList();
        List<Change> changes = changeRepository.findByIdIn(changeIds);
        Package packToZip = Package.builder()
                .status(PackageStatus.CREATED)
                .changes(changes)
                .build();
        packageRepository.save(packToZip);

        return createZip(packToZip);
    }

    @Transactional
    @Override
    public void deletePackage(Long packageId) {
        packageRepository.deleteById(packageId);
    }

    @Override
    public void createPackage(PackageDto packageDto) {
        List<Change> changes = changeRepository.findByIdIn(packageDto.getChangesId());
        Package pack = Package.builder()
                .status(PackageStatus.CREATED)
                .changes(changes)
                .build();
        packageRepository.save(pack);
    }

    @SneakyThrows
    private byte[] createZip(Package pack) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonPackage = mapper.writeValueAsString(pack);
        String secretHash = generateSecretHash(jsonPackage);
        String filename = "package.txt";
        String secretFileName = "secret.txt";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zout = new ZipOutputStream(baos)) {
            zout.putNextEntry(new ZipEntry(filename));
            zout.write(jsonPackage.getBytes(StandardCharsets.UTF_8));
            zout.closeEntry();

            zout.putNextEntry(new ZipEntry(secretFileName));
            zout.write(secretHash.getBytes(StandardCharsets.UTF_8));
            zout.closeEntry();

            return baos.toByteArray();
        } catch (Exception ex) {
            log.error("Error while creating ZIP: {}", ex.getMessage());
            throw new RuntimeException("Error while creating ZIP: " + ex.getMessage());
        }
    }

    private String generateSecretHash(String jsonPackage) {
        return DigestUtils.md5DigestAsHex(jsonPackage.getBytes()).toUpperCase();
    }
}
