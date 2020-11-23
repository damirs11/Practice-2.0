package ru.blogic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.blogic.entity.KeyMeta;
import ru.blogic.enums.LicenseType;

import java.util.UUID;

/**
 * DAO для мета данных ключа
 */
@Repository
@Transactional
public interface KeyRepository extends JpaRepository<KeyMeta, UUID> {
    Page<KeyMeta> findAllByLicenseType(LicenseType type, Pageable pageable);
}
