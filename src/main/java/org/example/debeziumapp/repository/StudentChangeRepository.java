package org.example.debeziumapp.repository;

import org.example.debeziumapp.entity.StudentChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentChangeRepository extends JpaRepository<StudentChange, Long> {

    List<StudentChange> findByTableNameIgnoreCase(String tableName);

    default void executeNativeSql(String sql) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.execute(sql);
        //todo выполнить sql скрипт
    }
}
