package com.tibame.group1.common.dto.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class CouponCreateReqDTO {
    @NotEmpty(message = "帳號請勿空白")
    @Pattern(
            regexp = "^[(一-龥)a-zA-Z0-9_]{1,30}$",
            message = "優惠卷名字：只能是中、英文字母、數字和_ , 且長度必需在1到30之間")
    private String title;

    @NotEmpty(message = "金額請勿空白")
    @Pattern(regexp = "^(0|1[0-4]?[0-9]{1,3}|15000)$",
            message = "最低金額：只能是正整數數字 , 且金額不能高於15000 , 低於1000")
    private String lowPrice;

    @NotEmpty(message = "折扣金額請勿空白")
    @Pattern(regexp = "^(1000|1[0-9]{3}|2000)$",
            message = "折扣金額：只能是正整數數字 , 且金額不能高於2000 , 低於100")
    private String discount;

    @NotEmpty(message = "數量請勿空白")
    @Pattern(regexp = "^(0|[1-9]\\d{0,4})$",
            message = "優惠卷數量：只能是正整數數字 , 且數量不能高於99999")
    private String number;

    @NotEmpty(message = "起始時間請勿空白")
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$",
            message = "時間格式必須為西元年月日(yyyymmdd)")
    private String dateStart;

    @NotEmpty(message = "結束時間請勿空白")
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$",
            message = "時間格式必須為西元年月日(yyyymmdd)")
    private String dateEnd;

    @NotEmpty(message = "請填上0或1 , (0代表可以疊加 / 1代表不能疊加)")
    @Pattern(regexp = "^[01]$",
            message = "可否疊加：0代表可以疊加 / 1代表不能疊加")
    private String addable;

    @NotEmpty(message = "請填上0或1 , (0代表可以使用 / 1代表不能使用)")
    @Pattern(regexp = "^[01]$",
            message = "激活狀態：0代表可以使用 / 1代表不能使用")
    private String livemode;
}
