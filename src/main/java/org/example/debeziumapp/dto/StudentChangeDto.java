package org.example.debeziumapp.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class StudentChangeDto {

    private Long id;

    private String sql;

    private String status;

    @Column(name = "table_name")
    private String tableName;
}
