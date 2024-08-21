package org.example.debeziumapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.StudentChangeDto;
import org.example.debeziumapp.entity.StudentChange;
import org.example.debeziumapp.mapper.StudentChangeMapper;
import org.example.debeziumapp.repository.StudentChangeRepository;
import org.example.debeziumapp.repository.StudentRepository;
import org.example.debeziumapp.service.api.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final StudentChangeRepository studentChangeRepository;

    private final StudentChangeMapper mapper;

    @Override
    public List<StudentChangeDto> findAll(String tableName) {
        List<StudentChange> studentChangeByName = studentChangeRepository.findByTableNameIgnoreCase(tableName);

        return studentChangeByName.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void executeChange(Long changeId) {
        StudentChange studentChange = studentChangeRepository.findById(changeId)
                .orElseThrow(() -> new RuntimeException("Change not found"));
        String studentChangeSql = studentChange.getSql();
        studentChangeRepository.executeNativeSql(studentChangeSql);
    }
}
