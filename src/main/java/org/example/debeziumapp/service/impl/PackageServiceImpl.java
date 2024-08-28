package org.example.debeziumapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.debeziumapp.dto.PackageDto;
import org.example.debeziumapp.entity.Change;
import org.example.debeziumapp.entity.Package;
import org.example.debeziumapp.entity.enums.PackageStatus;
import org.example.debeziumapp.repository.api.ChangeRepository;
import org.example.debeziumapp.repository.api.PackageRepository;
import org.example.debeziumapp.service.api.PackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final ChangeRepository changeRepository;

    private final PackageRepository packageRepository;

    @Override
    @Transactional
    public byte[] unloadPackage(PackageDto packagesDto) {
        List<Change> changes = changeRepository.findByIdIn(packagesDto.getChangesId());
        Package pack = Package.builder()
                .status(PackageStatus.CREATED)
                .changes(changes)
                .build();
        packageRepository.save(pack);

        return createZip(pack);
    }

    @SneakyThrows
    private byte[] createZip(Package pack) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonPackage = mapper.writeValueAsString(pack);
        String filename = "package.txt";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zout = new ZipOutputStream(baos)) {
            ZipEntry entry = new ZipEntry(filename);
            zout.putNextEntry(entry);
            byte[] buffer = jsonPackage.getBytes();
            zout.write(buffer);
            zout.closeEntry();

            return baos.toByteArray();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new byte[]{};
        }
    }
}
