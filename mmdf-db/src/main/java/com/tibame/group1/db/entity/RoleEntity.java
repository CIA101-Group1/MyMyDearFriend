package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Entity
@Table(name = "role")
@Immutable
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "role_name", nullable = false,length = 20)
    private String roleName;

    @Column(name = "role_description", nullable = false , length = 200)
    private String roleDescription;
}
