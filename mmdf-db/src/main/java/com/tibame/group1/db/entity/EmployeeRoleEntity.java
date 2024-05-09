package com.tibame.group1.db.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Entity
@Table(name = "employee_role")
@Immutable
public class EmployeeRoleEntity {
    /**
     * 資料庫id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
