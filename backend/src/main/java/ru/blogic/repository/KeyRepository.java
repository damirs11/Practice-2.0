package ru.blogic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blogic.entity.KeyMeta;

/**
 * DAO для мета данных ключа
 */
@Repository
public interface KeyRepository extends JpaRepository<KeyMeta, Long> {
}
