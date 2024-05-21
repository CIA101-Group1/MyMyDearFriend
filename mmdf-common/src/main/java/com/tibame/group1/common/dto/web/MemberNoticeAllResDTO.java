package com.tibame.group1.common.dto.web;

import com.tibame.group1.common.dto.PagesResDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberNoticeAllResDTO {

    private PagesResDTO pages;

    private List<MemberNoticeResDTO> memberNoticeList;
}
