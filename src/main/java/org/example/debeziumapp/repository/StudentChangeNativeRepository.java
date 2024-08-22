package org.example.debeziumapp.repository;

import lombok.RequiredArgsConstructor;
import org.example.debeziumapp.repository.api.StudentChangeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcStudentChangeRepository extends JdbcDaoSupport {

    public void executeNativeSql(String sql) {
        getJdbcTemplate().execute(sql);
    }
}
