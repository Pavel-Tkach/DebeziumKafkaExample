package org.example.debeziumapp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.debeziumapp.entity.enums.ChangeStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentChangeDto {

    private Long id;

    private String sql;

    @Enumerated(value = EnumType.STRING)
    private ChangeStatus status;

    @Column(name = "table_name")
    private String tableName;
}
