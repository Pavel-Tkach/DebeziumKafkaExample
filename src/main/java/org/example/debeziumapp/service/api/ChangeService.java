package org.example.debeziumapp.service.api;

import org.example.debeziumapp.dto.ChangeDto;

import java.util.List;

public interface ChangeService {

    List<ChangeDto> findAll(String tableName);

    void executeChange(Long changeId);
}
