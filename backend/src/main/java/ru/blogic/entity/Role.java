package ru.blogic.entity;

import ru.blogic.converter.RoleConverter;
import ru.blogic.enums.RoleNameImpl;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import ru.blogic.interfaces.RoleName;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Реализация интерфейса GrantedAuthority
 *
 * @author DSalikhov
 */
@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя
     */
    @Convert(converter = RoleConverter.class)
    @NaturalId
    private RoleName role;

    public Role() {
    }

    public Role(RoleNameImpl role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return role.getAuthority();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(RoleNameImpl name) {
        this.role = name;
    }
}