package ru.blogic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blogic.entity.KeyMeta;
import ru.blogic.enums.LicenseType;

import java.util.Collection;

/**
 * DAO для мета данных ключа
 */
@Repository
public interface KeyRepository extends JpaRepository<KeyMeta, Long> {
    Page<KeyMeta> findAllByType(LicenseType type, Pageable pageable);
}
