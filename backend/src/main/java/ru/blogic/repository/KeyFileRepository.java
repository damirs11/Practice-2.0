package ru.blogic.repository;

import ru.blogic.dto.KeyFileDTO;
import ru.blogic.entity.KeyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO для файлов ключа
 */
@Repository
public interface KeyFileRepository extends JpaRepository<KeyFileDTO, Long> {
}
