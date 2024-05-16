package com.tibame.group1.admin.service.Impl;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.service.EmployeeService;
import com.tibame.group1.admin.service.JwtService;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.CommonUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.EmployeeEntity;
import com.tibame.group1.db.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private JwtService jwtService;

    /**
     * 後台新增員工
     */
    @Override
    public EmployeeCreateResDTO employeeCreate(EmployeeCreateReqDTO req)
            throws DateException, IOException {
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

    /**
     * 後台員工查詢自己的資料
     */

    @Override
    public EmployeeDetailResDTO employeeDetail(AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException {
        EmployeeEntity employee = employeeRepository.findById(adminLoginSource.getEmployeeId()).orElse(null);
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
                null == employee.getEmployeeCreateTime() ? "" : DateUtils.dateToSting(employee.getEmployeeCreateTime()));
        resDTO.setEmployeeStatus(employee.getEmployeeStatus());
        return resDTO;
    }

    /**
     * 只有管理員能修改資料，目前先寫為員工自行更新自己的資料，待新增權限後修改
     */
    @Override
    public void employeeEdit(EmployeeEditReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        EmployeeEntity employee = employeeRepository.findById(adminLoginSource.getEmployeeId()).orElse(null);
        if (null == employee) {
            throw new CheckRequestErrorException("查無此會員資料");
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
        if(null != req.getEmployeePhone()){
            employee.setEmployeePhone(req.getEmployeePhone());
        }
        if (null != req.getEmployeeGender()) {
            employee.setEmployeeGender(req.getEmployeeGender());
        }
        employeeRepository.save(employee);

    }

    /**
     * 員工登入後台
     */
    @Override
    public LoginResDTO employeeLogin(AdminLoginReqDTO req) throws IOException{
        LoginResDTO resDTO = new LoginResDTO();
        //用輸入的帳號去資料庫撈相同的帳號的那一筆
        EmployeeEntity employee = employeeRepository.findByEmployeeAccount(req.getEmployeeAccount());
        //如果撈不到
        if (null == employee){
            //把response狀態設定為-1，表示帳號或密碼錯誤
            resDTO.setStatus(LoginResDTO.Status.LOGIN_INFO_INCORRECT.getCode());
            return resDTO;
        }
        //檢查帳號是否被停用
        if(employee.getEmployeeStatus() == 0){
            //帳號被停用，返回相應的錯誤消息
            resDTO.setStatus(LoginResDTO.Status.ACCOUNT_DISABLED.getCode());
            return resDTO;
        }
        /*帳號對了，下一步要對輸入的密碼跟原密碼有沒有一樣
        要使用經過MD5加密的密碼去比對
        如果不一樣
         */
        if(!CommonUtils.encryptToMD5(req.getEmployeePassword()).equals(employee.getEmployeePassword())){
            //把response狀態設定為-1，表示帳號或密碼錯誤
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
        //用jwt產生驗證碼，塞進去response
        resDTO.setAuthorization(jwtService.encodeLogin(adminLoginSource));
        return resDTO;

    }
}
