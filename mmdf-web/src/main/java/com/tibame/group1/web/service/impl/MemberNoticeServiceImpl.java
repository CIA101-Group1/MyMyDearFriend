package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeReqDTO;
import com.tibame.group1.common.dto.web.MemberNoticeResDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.common.utils.NumberUtils;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.entity.MemberNoticeEntity;
import com.tibame.group1.db.repository.MemberNoticeRepository;
import com.tibame.group1.db.specification.MemberNoticeSpecifications;
import com.tibame.group1.web.service.MemberNoticeService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberNoticeServiceImpl implements MemberNoticeService {
    @Autowired private MemberNoticeRepository memberNoticeRepository;

    @Override
    public MemberNoticeAllResDTO memberNoticeAll(MemberNoticeReqDTO req, Pageable pageable)
            throws DateException {
        MemberNoticeAllResDTO res = new MemberNoticeAllResDTO();

        Page<MemberNoticeEntity> pageResult;
        if (StringUtils.isEmpty(req.getSearchText())) {
            Specification<MemberNoticeEntity> spec =
                    Specification.where(MemberNoticeSpecifications.hasType(req.getNoticeCategory()))
                            .and(
                                    MemberNoticeSpecifications.timestampBetween(
                                            null == req.getSendingTimeBegin()
                                                    ? null
                                                    : DateUtils.stringToDate(
                                                            req.getSendingTimeBegin()),
                                            null == req.getSendingTimeEnd()
                                                    ? null
                                                    : DateUtils.stringToDate(
                                                            req.getSendingTimeEnd())));
            pageResult = memberNoticeRepository.findAll(spec, pageable);
        } else {
            pageResult =
                    memberNoticeRepository.findByMemberIdOrNoticeTitleOrNoticeContent(
                            NumberUtils.toInt(req.getSearchText()),
                            req.getSearchText(),
                            req.getSearchText(),
                            pageable);
        }

        List<MemberNoticeResDTO> memberNoticeList = new ArrayList<>();
        for (MemberNoticeEntity memberNotice : pageResult.getContent()) {
            MemberNoticeResDTO resDTO = new MemberNoticeResDTO();
            resDTO.setMemberNoticeId(String.valueOf(memberNotice.getMemberNoticeId()));
            resDTO.setMemberId(String.valueOf(memberNotice.getMember().getMemberId()));
            resDTO.setNoticeTitle(memberNotice.getNoticeTitle());
            resDTO.setNoticeContent(memberNotice.getNoticeContent());
            resDTO.setNoticeCategory(memberNotice.getNoticeCategory().getCode());
            resDTO.setSendingTime(
                    null == memberNotice.getSendingTime()
                            ? null
                            : DateUtils.dateToSting(memberNotice.getSendingTime()));
            resDTO.setLastReadingTime(
                    null == memberNotice.getLastReadingTime()
                            ? null
                            : DateUtils.dateToSting(memberNotice.getLastReadingTime()));
            memberNoticeList.add(resDTO);
        }
        PagesResDTO pagesResDTO = new PagesResDTO();
        pagesResDTO.setTotalPages(pageResult.getTotalPages());
        pagesResDTO.setTotalCount((int) pageResult.getTotalElements());
        res.setMemberNoticeList(memberNoticeList);
        res.setPages(pagesResDTO);
        return res;
    }
}
