package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Getter
@Setter
@Entity
//@Immutable
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(name = "employee_account", nullable = false, length = 20)
    private String employeeAccount;

    @Column(name = "employee_password", nullable = false, length = 1000)
    private String employeePassword;

    @Column(name = "employee_name", nullable = false, length = 20)
    private String employeeName;

    @Column(name = "employee_email", nullable = false, length = 50)
    private String employeeEmail;

    @Column(name = "employee_phone", nullable = false, length = 20)
    private String employeePhone;

    @Column(name = "employee_gender", nullable = false)
    private String employeeGender;

    @Column(name = "employee_createtime")
    private Date employeeCreateTime;

    @Column(name = "employee_status")
    private Integer employeeStatus;

}
