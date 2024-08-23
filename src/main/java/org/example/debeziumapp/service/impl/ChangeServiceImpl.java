package org.example.debeziumapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.dto.ChangeDto;
import org.example.debeziumapp.entity.Change;
import org.example.debeziumapp.mapper.ChangeMapper;
import org.example.debeziumapp.repository.ChangeNativeRepository;
import org.example.debeziumapp.repository.api.ChangeRepository;
import org.example.debeziumapp.service.api.ChangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeServiceImpl implements ChangeService {

    private final ChangeRepository changeRepository;

    private final ChangeMapper mapper;

    private final ChangeNativeRepository changeNativeRepository;

    @Override
    public List<ChangeDto> findAll(String tableName) {
        List<Change> changeByName = changeRepository.findAllByTableName(tableName);

        return changeByName.stream()
                .map(mapper::toDto)
                .toList();
    }


    @Transactional
    @Override
    public void executeChange(Long changeId) {
        Change change = changeRepository.findById(changeId)
                .orElseThrow(() -> new RuntimeException("Change not found"));
        String studentChangeSql = change.getSql();
        changeNativeRepository.executeNativeSql(studentChangeSql);
    }
}
