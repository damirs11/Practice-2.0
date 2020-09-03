package ru.blogic.repository;

import ru.blogic.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blogic.entity.KeyMetaDTO;

/**
 * DAO для мета данных ключа
 */
@Repository
public interface KeyRepository extends JpaRepository<KeyMetaDTO, Long> {
}
