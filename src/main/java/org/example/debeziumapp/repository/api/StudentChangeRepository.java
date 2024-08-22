package org.example.debeziumapp.repository.api;

import org.example.debeziumapp.entity.StudentChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentChangeRepository extends JpaRepository<StudentChange, Long> {

    List<StudentChange> findAllByTableName(String tableName);
}
