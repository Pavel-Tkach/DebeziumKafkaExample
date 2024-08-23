package org.example.debeziumapp.service;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.entity.Change;
import org.example.debeziumapp.entity.Student;
import org.example.debeziumapp.entity.enums.ChangeStatus;
import org.example.debeziumapp.repository.api.ChangeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeService {

    private final ChangeRepository changeRepository;

    public void executeStudentChanges(String before, Student student, String tableName) {
        String sqlScript = "null".equals(before) ? generateInsertSql(student, tableName) : generateUpdateSql(student, tableName);
        Change change = Change.builder()
                .sql(sqlScript)
                .status(ChangeStatus.CREATED)
                .tableName(tableName)
                .build();
        changeRepository.save(change);
    }

    private String generateInsertSql(Student student, String tableName) {
        return "INSERT INTO %s (name) VALUES ('%s')"
                .formatted(tableName, student.getName());
    }

    private String generateUpdateSql(Student student, String tableName) {
        return "UPDATE %s SET name = '%s' WHERE id = %d"
                .formatted(tableName, student.getName(), student.getId());
    }
}
