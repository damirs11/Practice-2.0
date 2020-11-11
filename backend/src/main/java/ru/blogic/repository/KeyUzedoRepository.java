package ru.blogic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blogic.entity.KeyMeta;
import ru.blogic.entity.KeyMetaUzedo;
import ru.blogic.enums.LicenseType;

/**
 * DAO для мета данных ключа
 */
@Repository
public interface KeyUzedoRepository extends JpaRepository<KeyMetaUzedo, Long> {
    Page<KeyMetaUzedo> findAllByType(LicenseType type, Pageable pageable);
}
