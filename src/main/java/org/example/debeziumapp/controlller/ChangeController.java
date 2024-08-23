package org.example.debeziumapp.controlller;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.ChangeDto;
import org.example.debeziumapp.service.api.ChangeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/changes")
@RequiredArgsConstructor
public class ChangeController {

    private final ChangeService changeService;

    @GetMapping
    public List<ChangeDto> findAll(@RequestParam String tableName) {
        return changeService.findAll(tableName);
    }

    @GetMapping("/{changeId}/execute")
    public void executeChange(@PathVariable Long changeId) {
        changeService.executeChange(changeId);
    }
}
