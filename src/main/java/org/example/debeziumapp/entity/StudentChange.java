package org.example.debeziumapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.debeziumapp.entity.enums.ChangeStatus;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sql;

    @Enumerated(value = EnumType.STRING)
    private ChangeStatus status;

    @Column(name = "table_name")
    private String tableName;
}
