package com.tibame.group1.admin.service;

import com.tibame.group1.common.utils.CommonUtils;
import com.tibame.group1.db.entity.EmployeeEntity;
import com.tibame.group1.db.entity.EmployeeRoleEntity;
import com.tibame.group1.db.entity.RoleEntity;
import com.tibame.group1.db.repository.EmployeeRepository;
import com.tibame.group1.db.repository.EmployeeRoleRepository;
import com.tibame.group1.db.repository.RoleRepository;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class InitTestEmployeeService {
    @Autowired private EmployeeRoleRepository employeeRoleRepository;

    @Autowired private RoleRepository roleRepository;

    @Autowired private EmployeeRepository employeeRepository;

    @Transactional(rollbackOn = Exception.class)
    public void init() throws IOException {
        // 如果不使用測試使用者，就不做事
        // if (!config.getUseTestMember()) {
        //     return;
        // }

        // 如果測試使用者資料已存在，就不做事
        EmployeeEntity employee = employeeRepository.findByEmployeeAccount("testAdmin");
        if (null != employee) {
            return;
        }

        log.info("執行初始化測試後台員工");

        // 建立測試使用者資料
        employee = new EmployeeEntity();
        employee.setEmployeeAccount("testAdmin");
        employee.setEmployeePassword(CommonUtils.encryptToMD5("A123456789"));
        employee.setEmployeeName("testAdmin");
        employee.setEmployeeEmail("testAdmin@example.com");
        employee.setEmployeePhone("0912345678");
        employee.setEmployeeGender("M");
        employee.setEmployeeCreateTime(new Date());
        employee.setEmployeeStatus(1);
        EmployeeEntity e1 = employeeRepository.save(employee);

        RoleEntity role1 = new RoleEntity();
        role1.setRoleName("超級管理員");
        role1.setRoleDescription("管理所有資訊");
        RoleEntity r1 = roleRepository.save(role1);

        RoleEntity role2 = new RoleEntity();
        role2.setRoleName("客服");
        role2.setRoleDescription("回應客服問題");
        roleRepository.save(role2);

        RoleEntity role3 = new RoleEntity();
        role3.setRoleName("活動管理員");
        role3.setRoleDescription("管理活動");
        roleRepository.save(role3);

        RoleEntity role4 = new RoleEntity();
        role4.setRoleName("一般員工");
        role4.setRoleDescription("一般員工");
        roleRepository.save(role4);

        // 创建一个新的EmployeeRoleEntity对象
        EmployeeRoleEntity employeeRole = new EmployeeRoleEntity();

        // 创建一个新的EmployeeRoleId对象并设置employeeId和roleId
        EmployeeRoleEntity.EmployeeRoleId id = new EmployeeRoleEntity.EmployeeRoleId();
        id.setEmployeeId(e1.getEmployeeId()); // 设置employeeId为已知的员工ID
        id.setRoleId(r1.getRoleId()); // 设置roleId为已知的角色ID

        // 设置EmployeeEntity和RoleEntity关联
        employeeRole.setId(id);

        employeeRole.setEmployeeId(e1);

        employeeRole.setRoleId(r1);

        // 将新的EmployeeRoleEntity对象保存到数据库
        employeeRoleRepository.save(employeeRole);

        log.info("完成初始化測試後台員工");
    }
}
