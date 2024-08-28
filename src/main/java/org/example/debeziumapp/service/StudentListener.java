package org.example.debeziumapp.service;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.entity.Change;
import org.example.debeziumapp.entity.enums.ChangeStatus;
import org.example.debeziumapp.repository.api.ChangeRepository;
import org.example.debeziumapp.util.JsonParser;
import org.example.debeziumapp.util.SqlGenerator;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentListener {

    private final JsonParser jsonParser;

    private final ChangeRepository changeRepository;

    private final SqlGenerator sqlGenerator;

    @KafkaListener(topics = "postgres.public.student", groupId = "student")
    public void listen(String jsonDataChanges) {
        String changeContent = jsonParser.getChangeContent(jsonDataChanges)[0];
        String tableName = jsonParser.getTableNameFromJson(jsonDataChanges);
        String before = jsonParser.getChangeContent(jsonDataChanges)[1];
        String after = jsonParser.getChangeContent(jsonDataChanges)[2];
        Map<String, String> parsedAfterData = jsonParser.parseAfterData(after);
        String sql = sqlGenerator.generateSql(before, tableName, parsedAfterData);
        changeRepository.save(Change.builder()
                .tableName(tableName)
                .status(ChangeStatus.CREATED)
                .sql(sql)
                .changeContent(changeContent).build());
    }
}
