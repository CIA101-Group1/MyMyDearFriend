package com.tibame.group1.admin.dto;



import com.tibame.group1.common.dto.PagesResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponResDTO {

    private PagesResDTO pages;

    private List<CouponAllResDTO> couponList;

}
