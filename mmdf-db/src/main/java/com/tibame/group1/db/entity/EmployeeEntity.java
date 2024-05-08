package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Immutable
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(name = "employee_account", nullable = false, length = 20)
    private String memberAccount;

    @Column(name = "employee_password", nullable = false, length = 200)
    private String employeePassword;

    @Column(name = "employee_name", nullable = false, length = 20)
    private String employeeName;

    @Column(name = "employee_email", nullable = false, length = 50)
    private String employeeEmail;

    @Column(name = "employee_phone", nullable = false, length = 20)
    private String employeePhone;

    private LocalDateTime employeeCreateTime;

    private Integer employeeStatus;

}
