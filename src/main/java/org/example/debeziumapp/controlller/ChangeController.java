package org.example.debeziumapp.controlller;
import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.ChangeDto;
import org.example.debeziumapp.dto.PackageDto;
import org.example.debeziumapp.service.api.ChangeService;
import org.example.debeziumapp.service.api.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/changes")
@RequiredArgsConstructor
public class ChangeController {

    private final ChangeService changeService;

    private final PackageService packageService;

    @GetMapping
    public List<ChangeDto> findAll(@RequestParam String tableName) {
        return changeService.findAll(tableName);
    }

    @GetMapping("/package/unloadPackage")
    public ResponseEntity<byte[]> unloadPackage(@RequestBody PackageDto packageDto) {
        return ResponseEntity.ok(packageService.unloadPackage(packageDto));
    }
}
