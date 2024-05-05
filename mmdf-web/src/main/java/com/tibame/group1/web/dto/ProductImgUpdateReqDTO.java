package com.tibame.group1.web.dto;

import com.tibame.group1.web.annotation.CheckLogin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImgUpdateReqDTO {

    private Integer imageId;

    private Integer productId;

    @NotEmpty(message = "請載入商品圖片以便審查及觀看")
    @Size(min = 1, message = "圖片大小：請提供至少 1byte")
    private byte[] image;

//    @CheckLogin
//    public Integer getMemberId() {
//        return null;
//    }

}
