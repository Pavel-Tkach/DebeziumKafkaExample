package org.example.debeziumapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.StudentChangeDto;
import org.example.debeziumapp.entity.StudentChange;
import org.example.debeziumapp.mapper.StudentChangeMapper;
import org.example.debeziumapp.repository.StudentChangeNativeRepository;
import org.example.debeziumapp.repository.api.StudentChangeRepository;
import org.example.debeziumapp.service.api.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final StudentChangeRepository studentChangeRepository;

    private final StudentChangeMapper mapper;

    private final StudentChangeNativeRepository studentChangeNativeRepository;

    @Override
    public List<StudentChangeDto> findAll(String tableName) {
        List<StudentChange> studentChangeByName = studentChangeRepository.findAllByTableName(tableName);

        return studentChangeByName.stream()
                .map(mapper::toDto)
                .toList();
    }


    @Transactional
    @Override
    public void executeChange(Long changeId) {
        StudentChange studentChange = studentChangeRepository.findById(changeId)
                .orElseThrow(() -> new RuntimeException("Change not found"));
        String studentChangeSql = studentChange.getSql();
        studentChangeNativeRepository.executeNativeSql(studentChangeSql);
    }
}
