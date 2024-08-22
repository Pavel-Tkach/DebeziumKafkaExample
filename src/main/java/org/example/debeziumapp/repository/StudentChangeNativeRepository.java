package org.example.debeziumapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class StudentChangeNativeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public void executeNativeSql(String sql) {
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
