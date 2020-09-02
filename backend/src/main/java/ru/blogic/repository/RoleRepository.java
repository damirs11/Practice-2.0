package ru.blogic.repository;

import ru.blogic.entity.Role;
import ru.blogic.enums.RoleNameImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO для ролей
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleNameImpl roles);
}
