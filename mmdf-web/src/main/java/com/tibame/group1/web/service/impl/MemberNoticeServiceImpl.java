package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeReqDTO;
import com.tibame.group1.common.dto.web.MemberNoticeResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeUpdateResDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.MemberNoticeEntity;
import com.tibame.group1.db.repository.MemberNoticeRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.specification.MemberNoticeSpecifications;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.MemberNoticeService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberNoticeServiceImpl implements MemberNoticeService {
    @Autowired private MemberNoticeRepository memberNoticeRepository;
    @Autowired private MemberRepository memberRepository;

    @Override
    public MemberNoticeAllResDTO memberNoticeAll(
            MemberNoticeReqDTO req, LoginSourceDTO loginSource, Pageable pageable)
            throws DateException {
        MemberNoticeAllResDTO res = new MemberNoticeAllResDTO();
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (null == member) {
            res.setStatus(MemberNoticeAllResDTO.Status.MEMBER_NOTFOUND.getCode());
            return res;
        }
        Page<MemberNoticeEntity> pageResult;
        Specification<MemberNoticeEntity> spec =
                Specification.where(MemberNoticeSpecifications.hasType(req.getNoticeCategory()))
                        .and(
                                MemberNoticeSpecifications.timestampBetween(
                                        null == req.getSendingTimeBegin()
                                                ? null
                                                : DateUtils.stringToDate(req.getSendingTimeBegin()),
                                        null == req.getSendingTimeEnd()
                                                ? null
                                                : DateUtils.stringToDate(req.getSendingTimeEnd())))
                        .and(MemberNoticeSpecifications.hasMemberId(member.getMemberId()));
        pageResult = memberNoticeRepository.findAll(spec, pageable);
        List<MemberNoticeResDTO> memberNoticeList = new ArrayList<>();
        int unReadCount = 0;
        for (MemberNoticeEntity memberNotice : pageResult.getContent()) {
            MemberNoticeResDTO resDTO = new MemberNoticeResDTO();
            resDTO.setNoticeTitle(memberNotice.getNoticeTitle());
            resDTO.setNoticeContent(memberNotice.getNoticeContent());
            resDTO.setNoticeCategory(memberNotice.getNoticeCategory().getCode());
            resDTO.setSendingTime(
                    null == memberNotice.getSendingTime()
                            ? null
                            : DateUtils.dateToSting(memberNotice.getSendingTime()));
            memberNoticeList.add(resDTO);
            if (null == memberNotice.getLastReadingTime()) {
                unReadCount++;
            }
        }
        PagesResDTO pagesResDTO = new PagesResDTO();
        pagesResDTO.setTotalPages(pageResult.getTotalPages());
        pagesResDTO.setTotalCount((int) pageResult.getTotalElements());
        res.setMemberNoticeList(memberNoticeList);
        res.setPages(pagesResDTO);
        res.setUnReadCount(unReadCount);
        res.setStatus(MemberNoticeAllResDTO.Status.QUERY_SUCCESS.getCode());
        return res;
    }

    @Override
    public MemberNoticeUpdateResDTO memberNoticeUpdateReadingTime(LoginSourceDTO loginSource) {
        MemberNoticeUpdateResDTO resDTO = new MemberNoticeUpdateResDTO();
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (null == member) {
            resDTO.setStatus(MemberNoticeUpdateResDTO.Status.MEMBER_NOTFOUND.getCode());
            return resDTO;
        }
        List<MemberNoticeEntity> memberNoticeList = memberNoticeRepository.findAll();
        for (MemberNoticeEntity memberNotice : memberNoticeList) {
            if (null == memberNotice.getLastReadingTime()) {
                memberNotice.setLastReadingTime(new Date());
            }
        }
        resDTO.setStatus(MemberNoticeUpdateResDTO.Status.UPDATE_SUCCESS.getCode());
        return resDTO;
    }
}
