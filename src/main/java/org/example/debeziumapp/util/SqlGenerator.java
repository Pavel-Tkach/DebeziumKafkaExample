package org.example.debeziumapp.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SqlGenerator {

    public String generateSql(String before, String tableName, Map<String, String> afterData) {
        return  "null".equals(before) ? generateInsertSql(tableName, afterData) : generateUpdateSql(tableName, afterData);
    }

    public String generateInsertSql(String tableName, Map<String, String> afterData) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName).append(" (");
        int count = 0;
        for (String key : afterData.keySet()) {
            builder.append(key);
            if (count < afterData.size() - 1) {
                builder.append(", ");
            }
            count++;
        }
        builder.append(") VALUES (");
        count = 0;
        for (String value : afterData.values()) {
            if (value.startsWith("\"") && value.endsWith("\"")) {
                String valueSql = value.replace('\"', '\'').trim();
                builder.append(valueSql);
            }
            else {
                builder.append(value);
            }
            if (count < afterData.size() - 1) {
                builder.append(", ");
            }
            count++;
        }
        builder.append(");");

        return builder.toString();
    }

    public String generateUpdateSql(String tableName, Map<String, String> afterData) {
        Map<String, String> whereData = new HashMap<>();
        whereData.put("id", afterData.get("id"));
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(tableName).append(" SET ");
        int count = 0;
        for (Map.Entry<String, String> entry : afterData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.append(key).append(" = ");
            if (value.startsWith("\"") && value.endsWith("\"")) {
                String valueSql = value.replace('\"', '\'').trim();
                builder.append(valueSql);
            }
            else {
                builder.append(value);
            }
            if (count < afterData.size() - 1) {
                builder.append(", ");
            }
            count++;
        }
        builder.append(" WHERE ").append("id = ").append(whereData.get("id"));
        builder.append(";");

        return builder.toString();
    }
}
