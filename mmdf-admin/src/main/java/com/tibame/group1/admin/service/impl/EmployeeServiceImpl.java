package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.service.EmployeeService;
import com.tibame.group1.admin.service.JwtService;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.utils.CommonUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.EmployeeEntity;
import com.tibame.group1.db.entity.EmployeeRoleEntity;
import com.tibame.group1.db.entity.RoleEntity;
import com.tibame.group1.db.repository.EmployeeRepository;
import com.tibame.group1.db.repository.EmployeeRoleRepository;
import com.tibame.group1.db.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired private EmployeeRepository employeeRepository;

    @Autowired private JwtService jwtService;

    @Autowired private EmployeeRoleRepository employeeRoleRepository;

    @Autowired private RoleRepository roleRepository;

    /** 後台新增員工 */
    @Override
    public EmployeeCreateResDTO employeeCreate(
            EmployeeCreateReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        // 確認用戶身份是否有效
        if (adminLoginSource == null || adminLoginSource.getEmployeeId() == null) {
            // 如果登錄信息不完整，拋出異常或返回錯誤提示
            throw new CheckRequestErrorException("登入異常");
        }
        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmployeeAccount(req.getEmployeeAccount());
        employee.setEmployeePassword(CommonUtils.encryptToMD5(req.getEmployeePassword()));
        employee.setEmployeeName(req.getEmployeeName());
        employee.setEmployeeEmail(req.getEmployeeEmail());
        employee.setEmployeePhone(req.getEmployeePhone());
        employee.setEmployeeGender(req.getEmployeeGender());
        employee.setEmployeeCreateTime(new Date());
        employee.setEmployeeStatus(1);
        employee = employeeRepository.save(employee);
        EmployeeCreateResDTO resDTO = new EmployeeCreateResDTO();
        resDTO.setEmployeeId(employee.getEmployeeId());
        return resDTO;
    }

    /** 後台員工查詢自己的資料 */
    @Override
    public EmployeeDetailResDTO employeeDetail(AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException {
        EmployeeEntity employee =
                employeeRepository.findById(adminLoginSource.getEmployeeId()).orElse(null);
        if (null == employee) {
            throw new CheckRequestErrorException("查無此員工資料");
        }
        EmployeeRoleEntity employeeRoleEntities =
                employeeRoleRepository.findRoleIdByEmployeeId(adminLoginSource.getEmployeeId());

        EmployeeDetailResDTO resDTO = new EmployeeDetailResDTO();
        resDTO.setEmployeeId(employee.getEmployeeId());
        resDTO.setEmployeeAccount(employee.getEmployeeAccount());
        resDTO.setEmployeePassword(employee.getEmployeePassword());
        resDTO.setEmployeeName(employee.getEmployeeName());
        resDTO.setEmployeeEmail(employee.getEmployeeEmail());
        resDTO.setEmployeePhone(employee.getEmployeePhone());
        resDTO.setEmployeeGender(employee.getEmployeeGender());
        resDTO.setEmployeeCreateTime(
                null == employee.getEmployeeCreateTime()
                        ? ""
                        : DateUtils.dateToSting(employee.getEmployeeCreateTime()));
        resDTO.setEmployeeStatus(employee.getEmployeeStatus());
        resDTO.setRoleName(employeeRoleEntities.getRoleId().getRoleName());
        resDTO.setRoleDescription(employeeRoleEntities.getRoleId().getRoleDescription());
        return resDTO;
    }

    @Override
    public EmployeeDetailResDTO employeeDetailById(
            AdminLoginSourceDTO adminLoginSource, Integer employeeId)
            throws CheckRequestErrorException {
        EmployeeEntity employee = employeeRepository.findById(employeeId).orElse(null);
        if (null == employee) {
            throw new CheckRequestErrorException("查無此員工資料");
        }
        EmployeeDetailResDTO resDTO = new EmployeeDetailResDTO();
        resDTO.setEmployeeId(employee.getEmployeeId());
        resDTO.setEmployeeAccount(employee.getEmployeeAccount());
        resDTO.setEmployeePassword(employee.getEmployeePassword());
        resDTO.setEmployeeName(employee.getEmployeeName());
        resDTO.setEmployeeEmail(employee.getEmployeeEmail());
        resDTO.setEmployeePhone(employee.getEmployeePhone());
        resDTO.setEmployeeGender(employee.getEmployeeGender());
        resDTO.setEmployeeCreateTime(
                null == employee.getEmployeeCreateTime()
                        ? ""
                        : DateUtils.dateToSting(employee.getEmployeeCreateTime()));
        resDTO.setEmployeeStatus(employee.getEmployeeStatus());
        return resDTO;
    }

    /**
     * 用name查詢員工
     *
     * @param adminLoginSourceDTO
     * @param employeeName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeAllResDTO> employeeAll(
            AdminLoginSourceDTO adminLoginSourceDTO, String employeeName) {

        List<EmployeeEntity> employees;

        if (employeeName == null || employeeName.isEmpty()) {
            // 如果 employeeName 为空，则直接查询所有员工
            employees = employeeRepository.findAll();
        } else {
            // 如果 employeeName 不为空，则使用 ExampleMatcher 进行匹配查询
            EmployeeEntity exampleEntity = new EmployeeEntity();
            exampleEntity.setEmployeeName(employeeName);

            // 使用包含匹配器
            ExampleMatcher matcher =
                    ExampleMatcher.matching()
                            .withMatcher(
                                    "employeeName",
                                    ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            // 将示例对象和匹配器组合成 Example 对象
            Example<EmployeeEntity> example = Example.of(exampleEntity, matcher);

            // 调用 findAll 方法进行查询
            employees = employeeRepository.findAll(example);
        }

        // 将查询结果转换为 EmployeeAllResDTO 对象
        List<EmployeeAllResDTO> employeeList = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            EmployeeAllResDTO resDTO = new EmployeeAllResDTO();
            resDTO.setEmployeeId(String.valueOf(employee.getEmployeeId()));
            resDTO.setEmployeeAccount(employee.getEmployeeAccount());
            resDTO.setEmployeePassword(employee.getEmployeePassword());
            resDTO.setEmployeeName(employee.getEmployeeName());
            resDTO.setEmployeeEmail(employee.getEmployeeEmail());
            resDTO.setEmployeePhone(employee.getEmployeePhone());
            resDTO.setEmployeeCreateTime(DateUtils.dateToSting(employee.getEmployeeCreateTime()));
            resDTO.setEmployeeStatus(employee.getEmployeeStatus());
            employeeList.add(resDTO);
        }
        return employeeList;
    }

    @Override
    public EmployeeEditResDTO employeeEdit(
            EmployeeEditReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        EmployeeEditResDTO resDTO = new EmployeeEditResDTO();
        EmployeeEntity employee = employeeRepository.findById(req.getEmployeeId()).orElse(null);
        if (null == employee) {
            resDTO.setStatus(EmployeeEditResDTO.Status.EMPLOYEE_NOTFOUND.getCode());
            return resDTO;
        }
        if ((null != req.getEmployeeAccount())) {
            employee.setEmployeeAccount(req.getEmployeeAccount());
        }
        if (null != req.getEmployeePassword()) {
            employee.setEmployeePassword(CommonUtils.encryptToMD5(req.getEmployeePassword()));
        }
        if (null != req.getEmployeeName()) {
            employee.setEmployeeName(req.getEmployeeName());
        }
        if (null != req.getEmployeeEmail()) {
            employee.setEmployeeEmail(req.getEmployeeEmail());
        }
        if (null != req.getEmployeePhone()) {
            employee.setEmployeePhone(req.getEmployeePhone());
        }
        if (null != req.getEmployeeGender()) {
            employee.setEmployeeGender(req.getEmployeeGender());
        }
        if (null != req.getEmployeeStatus()) {
            employee.setEmployeeStatus(req.getEmployeeStatus());
        }
        employeeRepository.save(employee);
        resDTO.setStatus(EmployeeEditResDTO.Status.EDIT_SUCCESS.getCode());
        return resDTO;
    }

    /** 員工登入後台 */
    @Override
    public LoginResDTO employeeLogin(AdminLoginReqDTO req) throws IOException {
        LoginResDTO resDTO = new LoginResDTO();
        // 用輸入的帳號去資料庫撈相同的帳號的那一筆
        EmployeeEntity employee =
                employeeRepository.findByEmployeeAccount(req.getEmployeeAccount());
        // 如果撈不到
        if (null == employee) {
            // 把response狀態設定為-1，表示帳號或密碼錯誤
            resDTO.setStatus(LoginResDTO.Status.LOGIN_INFO_INCORRECT.getCode());
            return resDTO;
        }
        // 檢查帳號是否被停用
        if (employee.getEmployeeStatus() == 0) {
            // 帳號被停用，返回相應的錯誤消息
            resDTO.setStatus(LoginResDTO.Status.ACCOUNT_DISABLED.getCode());
            return resDTO;
        }
        /*帳號對了，下一步要對輸入的密碼跟原密碼有沒有一樣
        要使用經過MD5加密的密碼去比對
        如果不一樣
         */
        if (!CommonUtils.encryptToMD5(req.getEmployeePassword())
                .equals(employee.getEmployeePassword())) {
            // 把response狀態設定為-1，表示帳號或密碼錯誤
            resDTO.setStatus(LoginResDTO.Status.LOGIN_INFO_INCORRECT.getCode());
            return resDTO;
        }
        /*密碼正確後，下一步要產登入驗證碼，用在後面需要登入的頁面
        先設定loginSource裡面代的基本資訊
         */
        resDTO.setStatus(LoginResDTO.Status.LOGIN_SUCCESS.getCode());
        AdminLoginSourceDTO adminLoginSource = new AdminLoginSourceDTO();
        adminLoginSource.setEmployeeId(employee.getEmployeeId());
        adminLoginSource.setEmployeeName(employee.getEmployeeName());
        // 用jwt產生驗證碼，塞進去response
        resDTO.setAuthorization(jwtService.encodeLogin(adminLoginSource));
        return resDTO;
    }

    // 設定員工權限
    @Override
    @Transactional
    public EmployeeRoleResDTO assignRoleToEmployee(
            EmployeeRoleReqDTO employeeRoleReq, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException {

        // 查找員工是否已註冊
        EmployeeEntity employee =
                employeeRepository.findById(employeeRoleReq.getEmployeeId()).orElse(null);
        if (employee == null) {
            throw new CheckRequestErrorException("查無員工資料");
        }

        // 檢查roleId是否為空
        if (employeeRoleReq.getRoleId() == null) {
            throw new CheckRequestErrorException("查無角色資料");
        }

        // 檢查角色是否存在
        RoleEntity role =
                roleRepository
                        .findById(employeeRoleReq.getRoleId())
                        .orElseThrow(() -> new CheckRequestErrorException("查不到對應角色"));

        // 創建複合主鍵
        EmployeeRoleEntity.EmployeeRoleId employeeRoleId = new EmployeeRoleEntity.EmployeeRoleId();
        employeeRoleId.setEmployeeId(employeeRoleReq.getEmployeeId());
        employeeRoleId.setRoleId(employeeRoleReq.getRoleId());

        // 創建並保存紀錄
        EmployeeRoleEntity employeeRole = new EmployeeRoleEntity();
        employeeRole.setId(employeeRoleId);
        employeeRole.setEmployeeId(employee);
        employeeRole.setRoleId(role);
        employeeRoleRepository.save(employeeRole);

        // 創建並返回
        EmployeeRoleResDTO employeeRoleResDTO = new EmployeeRoleResDTO();
        employeeRoleResDTO.setEmployeeId(employeeRoleReq.getEmployeeId());
        employeeRoleResDTO.setRoleId(employeeRoleReq.getRoleId());

        return employeeRoleResDTO;
    }

    // 查詢員工權限
    @Override
    public EmployeeRoleDetailResDTO employeeRole(AdminLoginSourceDTO adminLoginSource, Integer employeeId) {
        EmployeeRoleEntity employeeRoleEntities =
                employeeRoleRepository.findRoleIdByEmployeeId(employeeId);

     EmployeeRoleDetailResDTO resDTO = new EmployeeRoleDetailResDTO();
        resDTO.setEmployeeName(employeeRoleEntities.getEmployeeId().getEmployeeName());
        resDTO.setRoleName(employeeRoleEntities.getRoleId().getRoleName());
        resDTO.setRoleDescription(employeeRoleEntities.getRoleId().getRoleDescription());
        return resDTO;
    }
}
