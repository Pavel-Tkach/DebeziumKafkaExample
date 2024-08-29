package org.example.debeziumapp.controlller;
import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.PackageDto;
import org.example.debeziumapp.service.api.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/changes")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping("/package/create")
    public void createPackage(@RequestBody PackageDto packageDto) {
        packageService.createPackage(packageDto);
    }

    @GetMapping("/package/unloadPackage/{packageId}")
    public ResponseEntity<byte[]> unloadPackage(@PathVariable Long packageId) {
        return ResponseEntity.ok(packageService.unloadPackage(packageId));
    }

    @DeleteMapping("/package/{packageId}")
    public void deletePackage(@PathVariable Long packageId) {
        packageService.deletePackage(packageId);
    }
}
