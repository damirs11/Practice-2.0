package com.example.repository;

import com.example.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO для мета данных ключа
 */
@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
}
