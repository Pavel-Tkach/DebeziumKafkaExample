package org.example.debeziumapp.service;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.entity.Student;
import org.example.debeziumapp.entity.StudentChange;
import org.example.debeziumapp.entity.enums.ChangeStatus;
import org.example.debeziumapp.repository.StudentChangeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentChangesService {

    private final StudentChangeRepository studentChangeRepository;

    public void executeStudentChanges(String before, Student student, String tableName) {
        String sqlScript = Objects.isNull(before) ? generateInsertSql(student, tableName) : generateUpdateSql(student, tableName);
        StudentChange studentChange = StudentChange.builder()
                .sql(sqlScript)
                .status(ChangeStatus.CREATED)
                .tableName(tableName)
                .build();
        studentChangeRepository.save(studentChange);
    }

    private String generateInsertSql(Student student, String tableName) {
        return "INSERT INTO %s (id, name) VALUES (%d, %s)"
                .formatted(tableName, student.getId(), student.getName());
    }

    private String generateUpdateSql(Student student, String tableName) {
        return "UPDATE %s SET name = %s WHERE id = %d"
                .formatted(tableName, student.getName(), student.getId());
    }
}
