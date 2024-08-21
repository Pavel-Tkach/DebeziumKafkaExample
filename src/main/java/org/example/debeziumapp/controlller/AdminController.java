package org.example.debeziumapp.controlller;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.StudentChangeDto;
import org.example.debeziumapp.service.api.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/changes")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<StudentChangeDto> findAll(@RequestParam String tableName) {
        return adminService.findAll(tableName);
    }

    @PostMapping("/{changeId}")
    public void executeChange(@PathVariable Long changeId) {
        adminService.executeChange(changeId);
    }
}
