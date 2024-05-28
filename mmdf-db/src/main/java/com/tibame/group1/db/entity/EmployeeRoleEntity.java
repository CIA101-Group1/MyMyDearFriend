package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "employee_role")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRoleEntity {
    //嵌入式主鍵
    @EmbeddedId
    private EmployeeRoleId id;


    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeId;


    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity roleId;

    @Setter
    @Getter
    @Embeddable
    public static class EmployeeRoleId implements Serializable {
        private Integer employeeId;
        private Integer roleId;

        //覆寫equals()方法
        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmployeeRoleId that = (EmployeeRoleId) o;
            return Objects.equals(employeeId, that.getEmployeeId()) &&
                    Objects.equals(roleId, that.getRoleId());
        }

        //覆寫hashCode()方法
        @Override
        public int hashCode() {
            return Objects.hash(employeeId, roleId);
        }
    }
}


