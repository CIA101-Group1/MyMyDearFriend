package com.tibame.group1.common.handler;

import com.tibame.group1.common.dto.ParamErrorDTO;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.enums.StatusCode;
import com.tibame.group1.common.exception.*;
import com.tibame.group1.common.utils.ExceptionUtils;
import com.tibame.group1.common.utils.StringUtils;

import jakarta.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    /** 查無http服務錯誤處理 */
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleNoHandlerFoundException() {
        return new ResDTO<>(StatusCode.HTTP_ERROR);
    }

    /** http傳輸資訊錯誤處理 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleHttpMessageNotReadableException() {
        return new ResDTO<>(StatusCode.REQUEST_FORMAT_ERROR);
    }

    /** http method錯誤處理 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleHttpRequestMethodNotSupportedException() {
        return new ResDTO<>(StatusCode.HTTP_METHOD_ERROR);
    }

    /** 傳入資料格式檢核錯誤處理 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<List<ParamErrorDTO>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ResDTO<List<ParamErrorDTO>> res = new ResDTO<>(StatusCode.REQUEST_FORMAT_ERROR);
        res.setData(new ArrayList<>());
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrorList = result.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            ParamErrorDTO data = new ParamErrorDTO();
            data.setKey(fieldError.getField());
            data.setErrorMessage(fieldError.getDefaultMessage());
            res.getData().add(data);
        }
        return res;
    }

    /** 傳入資料格式檢核錯誤處理 */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<List<ParamErrorDTO>> handleMethodArgumentNotValidException(
            MethodArgumentTypeMismatchException e) {
        ResDTO<List<ParamErrorDTO>> res = new ResDTO<>(StatusCode.REQUEST_FORMAT_ERROR);
        res.setData(new ArrayList<>());
        ParamErrorDTO data = new ParamErrorDTO();
        data.setKey(e.getParameter().getParameterName());
        data.setErrorMessage(e.getLocalizedMessage());
        res.getData().add(data);
        return res;
    }

    /** 傳入資料鍵值缺少錯誤處理 */
    @ExceptionHandler({
        MissingServletRequestParameterException.class,
        MissingServletRequestPartException.class
    })
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleMissingServletRequestException() {
        return new ResDTO<>(StatusCode.REQUEST_KEY_ERROR);
    }

    /** 上傳檔案超過大小限制錯誤處理 */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleMaxUploadSizeExceededException() {
        return new ResDTO<>(StatusCode.FILE_TOO_BIG);
    }

    /** 時間或日期格式錯誤處理 */
    @ExceptionHandler(DateException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleCheckTimeException() {
        return new ResDTO<>(StatusCode.TIME_FORMAT_ERROR);
    }

    /** 類別代碼錯誤處理 */
    @ExceptionHandler(EnumCodeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleCheckTimeException(EnumCodeNotFoundException e) {
        ResDTO<?> res = new ResDTO<>();
        res.setStatusCode(StatusCode.REQUEST_DATA_CHECK_FAIL);
        if (!StringUtils.isEmpty(e.getMessage())) res.setMessage(e.getMessage());
        return res;
    }

    /** 資料已存在錯誤處理 */
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleExistException(AlreadyExistException e) {
        ResDTO<?> res = new ResDTO<>(StatusCode.EXIST_ERROR);
        if (!StringUtils.isEmpty(e.getMessage())) res.setMessage(e.getMessage());
        return res;
    }

    /** 檢查傳入資料不符合處理 */
    @ExceptionHandler(CheckRequestErrorException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleCheckRequestErrorException(CheckRequestErrorException e) {
        ResDTO<?> res = new ResDTO<>();
        res.setStatusCode(StatusCode.REQUEST_DATA_CHECK_FAIL);
        if (!StringUtils.isEmpty(e.getMessage())) res.setMessage(e.getMessage());
        return res;
    }

    /** 資料格式檢核錯誤處理 */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleValidationException(ValidationException e) {
        ResDTO<?> res = new ResDTO<>();
        res.setStatusCode(StatusCode.REQUEST_FORMAT_ERROR);
        if (!StringUtils.isEmpty(e.getMessage())) res.setMessage(e.getMessage());
        return res;
    }

    /** 登入驗證碼錯誤處理 */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleAuthorizationException(AuthorizationException e) {
        ResDTO<?> res = new ResDTO<>();
        res.setStatusCode(StatusCode.AUTHORIZATION_ERROR);
        if (!StringUtils.isEmpty(e.getMessage())) res.setMessage(e.getMessage());
        return res;
    }

    /** 資料庫處理錯誤處理 */
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleSQLException(Exception e) {
        ResDTO<?> res = new ResDTO<>(StatusCode.SQL_ERROR);
        log.error("資料庫處理錯誤，錯誤資訊：" + ExceptionUtils.getErrorDetail(e));
        return res;
    }

    /** IO錯誤 */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleIOException(IOException e) {
        ResDTO<?> res = new ResDTO<>(StatusCode.CUSTOM_ERROR);
        res.setMessage(e.getMessage());
        log.error("伺服器處理失敗，錯誤資訊：" + ExceptionUtils.getErrorDetail(e));
        return res;
    }

    /** 其他錯誤處理 */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResDTO<?> handleException(Exception e) {
        ResDTO<?> res = new ResDTO<>(StatusCode.UNKNOWN_ERROR);
        log.error("發生不可預期的錯誤，錯誤資訊：" + ExceptionUtils.getErrorDetail(e));
        return res;
    }
}
