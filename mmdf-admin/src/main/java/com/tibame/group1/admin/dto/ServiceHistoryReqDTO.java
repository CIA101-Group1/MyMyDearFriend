package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceHistoryReqDTO {
    private String type;
    private Integer memberId;
    private String dateStar;
    private String dateEnd;
    private Integer month;
    private Integer year;
}
