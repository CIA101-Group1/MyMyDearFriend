package com.tibame.group1.admin.service.Impl;

import com.tibame.group1.admin.dto.MemberAllReqDTO;
import com.tibame.group1.admin.dto.MemberAllResDTO;
import com.tibame.group1.admin.dto.MemberResDTO;
import com.tibame.group1.admin.service.MemberService;
import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired private MemberRepository memberRepository;

    @Override
    public MemberResDTO memberAll(MemberAllReqDTO req, Pageable pageable) {
        MemberEntity exampleEntity = new MemberEntity();
        exampleEntity.setIsVerified(req.getIsVerified());
        exampleEntity.setSellerStatus(req.getSellerStatus());

        // 使用ExampleMatcher定義匹配規則，這裡使用默認的匹配器，即全匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 將範例對象和匹配規則組合成Example對象
        Example<MemberEntity> example = Example.of(exampleEntity, matcher);

        // 調用findAll方法進行查詢和分頁
        Page<MemberEntity> pageResult = memberRepository.findAll(example, pageable);

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
            resDTO.setTwPersonId(member.getTwPersonId());
            resDTO.setCity(member.getCity());
            resDTO.setAddress(member.getAddress());
            resDTO.setIsVerified(member.getIsVerified());
            resDTO.setSellerStatus(member.getSellerStatus());
            resDTO.setScoreNumber(String.valueOf(member.getScoreNumber()));
            resDTO.setScoreSum(String.valueOf(member.getScoreSum()));
            resDTO.setImageBase64(
                    null == member.getImage()
                            ? null
                            : ConvertUtils.bytesToBase64(member.getImage()));
            memberList.add(resDTO);
        }
        PagesResDTO pagesResDTO = new PagesResDTO();
        pagesResDTO.setTotalPage(String.valueOf(pageResult.getTotalPages()));
        pagesResDTO.setTotalCount(String.valueOf(pageResult.getTotalElements()));
        MemberResDTO res = new MemberResDTO();
        res.setMemberList(memberList);
        res.setPages(pagesResDTO);
        return res;
    }
}
