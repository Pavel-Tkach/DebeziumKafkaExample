package org.example.debeziumapp.util;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.entity.Student;
import org.example.debeziumapp.exception.TableNameNotSpecifiedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JsonParser {

    public String[] getBeforeAndAfterFromJson(String json) {
        String pattern = "\"before\":(.*?),(\"after\":(\\{.*?}))";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(json);
        String before = "";
        String after = "";
        if (matcher.find()) {
            before = matcher.group(1);
            after = matcher.group(3);
        }

        return new String[]{before, after};
    }

    public String getTableNameFromJson(String json) {
        String patternTable = "\"table\":\\s*\"([^\"]+)\"";
        Pattern compiledTable = Pattern.compile(patternTable);
        Matcher matcher = compiledTable.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new TableNameNotSpecifiedException("Table name not specified");
    }

    public Student parseAfterData(String afterData) {
        List<String> valuesFromTable = new ArrayList<>();
        String columnsPattern = ":\\s*([^,}]+)";
        Pattern pattern = Pattern.compile(columnsPattern);
        Matcher matcher = pattern.matcher(afterData);
        while (matcher.find()) {
            String value = matcher.group(1);
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            valuesFromTable.add(value);
        }
        //todo жесткая привязка к таблице Student
        Long id = Long.valueOf(valuesFromTable.get(0));
        String name = valuesFromTable.get(1);

        return new Student(id, name);
    }
}
