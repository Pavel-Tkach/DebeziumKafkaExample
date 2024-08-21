package org.example.debeziumapp.service.api;

import org.example.debeziumapp.dto.StudentChangeDto;

import java.util.List;

public interface AdminService {

    List<StudentChangeDto> findAll(String tableName);

    void executeChange(Long changeId);
}
