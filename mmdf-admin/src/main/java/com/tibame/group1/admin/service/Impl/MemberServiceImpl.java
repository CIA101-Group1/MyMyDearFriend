package com.tibame.group1.admin.service.Impl;

import com.tibame.group1.admin.dto.MemberAllReqDTO;
import com.tibame.group1.admin.dto.MemberAllResDTO;
import com.tibame.group1.admin.dto.MemberResDTO;
import com.tibame.group1.admin.service.MemberService;
import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.common.utils.NumberUtils;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired private MemberRepository memberRepository;

    @Override
    public MemberResDTO memberAll(MemberAllReqDTO req, Pageable pageable) {
        Page<MemberEntity> pageResult;
        if (StringUtils.isEmpty(req.getSearchText())) {
            MemberEntity filterEntity = new MemberEntity();
            filterEntity.setIsVerified(req.getIsVerified());
            filterEntity.setSellerStatus(req.getSellerStatus());
            ExampleMatcher filterMatcher = ExampleMatcher.matching();
            // 將範例對象和匹配規則組合成Example對象
            Example<MemberEntity> filterExample = Example.of(filterEntity, filterMatcher);
            // 調用findAll方法進行查詢和分頁
            pageResult = memberRepository.findAll(filterExample, pageable);
        } else {
            pageResult =
                    memberRepository.findByMemberIdOrMemberAccountOrEmail(
                            NumberUtils.toInt(req.getSearchText()),
                            req.getSearchText(),
                            req.getSearchText(),
                            pageable);
        }

        // 把查詢結果從Page物件拿出來塞進去List裡面
        List<MemberAllResDTO> memberList = new ArrayList<>();
        for (MemberEntity member : pageResult.getContent()) {
            MemberAllResDTO resDTO = new MemberAllResDTO();
            resDTO.setMemberId(String.valueOf(member.getMemberId()));
            resDTO.setMemberAccount(member.getMemberAccount());
            resDTO.setName(member.getName());
            resDTO.setPhone(member.getPhone());
            resDTO.setEmail(member.getEmail());
            resDTO.setBirth(DateUtils.dateToSting(member.getBirth()));
            resDTO.setCity(member.getCity());
            resDTO.setAddress(member.getAddress());
            resDTO.setIsVerified(member.getIsVerified());
            resDTO.setVerifySendingTime(
                    null == member.getVerifySendingTime()
                            ? null
                            : DateUtils.dateToSting(member.getVerifySendingTime()));
            resDTO.setVerifiedTime(
                    null == member.getVerifiedTime()
                            ? null
                            : DateUtils.dateToSting(member.getVerifiedTime()));
            resDTO.setJoinTime(DateUtils.dateToSting(member.getJoinTime()));
            resDTO.setWalletAmount(member.getWalletAmount());
            resDTO.setWalletAvailableAmount(member.getWalletAvailableAmount());
            resDTO.setSellerStatus(member.getSellerStatus());
            resDTO.setScoreNumber(member.getScoreNumber());
            resDTO.setScoreSum(member.getScoreSum());
            resDTO.setImageBase64(
                    null == member.getImage()
                            ? null
                            : ConvertUtils.bytesToBase64(member.getImage()));
            resDTO.setCidResetSendingTime(
                    null == member.getCidResetSendingTime()
                            ? null
                            : DateUtils.dateToSting(member.getCidResetSendingTime()));
            memberList.add(resDTO);
        }
        PagesResDTO pagesResDTO = new PagesResDTO();
        pagesResDTO.setTotalPages(pageResult.getTotalPages());
        pagesResDTO.setTotalCount((int) pageResult.getTotalElements());
        MemberResDTO res = new MemberResDTO();
        res.setMemberList(memberList);
        res.setPages(pagesResDTO);
        return res;
    }
}
