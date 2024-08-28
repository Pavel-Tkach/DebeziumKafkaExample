package org.example.debeziumapp.util;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.exception.TableNameNotSpecifiedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JsonParser {

    public String[] getChangeContent(String json) {
        String pattern = "\"before\":(.*?),(\"after\":(\\{.*?}))";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(json);
        String before = "";
        String after = "";
        if (matcher.find()) {
            before = matcher.group(1);
            after = matcher.group(3);
        }

        return new String[] {"\"before\":\"%s\",\"after\":\"%s\"".formatted(before, after), before, after};
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

    public Map<String, String> parseAfterData(String afterData) {
        Map<String, String> fieldValueMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(\\w+)\"\\s*:\\s*([^,}]+)");
        Matcher matcher = pattern.matcher(afterData);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            fieldValueMap.put(key, value);
        }

        return fieldValueMap;
    }
}
