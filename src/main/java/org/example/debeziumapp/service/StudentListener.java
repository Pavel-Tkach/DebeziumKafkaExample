package org.example.debeziumapp.service;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.entity.Student;
import org.example.debeziumapp.util.JsonParser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentListener {

    private final JsonParser jsonParser;

    private final StudentChangesService studentChangesService;

    @KafkaListener(topics = "postgres.public.student", groupId = "student")
    public void listen(String jsonDataChanges) {
        String[] beforeAndAfterData = jsonParser.getBeforeAndAfterFromJson(jsonDataChanges);
        String before = beforeAndAfterData[0];
        Student student = jsonParser.parseAfterData(beforeAndAfterData[1]);
        String tableName = jsonParser.getTableNameFromJson(jsonDataChanges);
        studentChangesService.executeStudentChanges(before, student, tableName);
    }
}
