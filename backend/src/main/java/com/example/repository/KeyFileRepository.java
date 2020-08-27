package com.example.repository;

import com.example.entity.KeyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO для файлов ключа
 */
@Repository
public interface KeyFileRepository extends JpaRepository<KeyFile, Long> {
}
