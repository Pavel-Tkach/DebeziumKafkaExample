package org.example.debeziumapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.debeziumapp.entity.enums.ChangeStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "student_change")
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
