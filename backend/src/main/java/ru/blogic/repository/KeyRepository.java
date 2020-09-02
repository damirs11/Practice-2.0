package ru.blogic.repository;

import ru.blogic.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO для мета данных ключа
 */
@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
}
