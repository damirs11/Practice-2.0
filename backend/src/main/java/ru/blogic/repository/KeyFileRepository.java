package ru.blogic.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.blogic.entity.KeyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO для файлов ключа
 */
@Repository
@Transactional
public interface KeyFileRepository extends JpaRepository<KeyFile, UUID> {
}
