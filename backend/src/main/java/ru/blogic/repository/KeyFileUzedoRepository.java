package ru.blogic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyFileUzedo;

/**
 * DAO для файлов ключа
 */
@Repository
public interface KeyFileUzedoRepository extends JpaRepository<KeyFileUzedo, Long> {
}
