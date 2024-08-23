package org.example.debeziumapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeNativeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public void executeNativeSql(String sql) {
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
