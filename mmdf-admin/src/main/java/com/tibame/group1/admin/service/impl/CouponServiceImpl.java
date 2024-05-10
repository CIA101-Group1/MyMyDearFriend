package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.CouponAllReqDTO;
import com.tibame.group1.admin.dto.CouponAllResDTO;
import com.tibame.group1.admin.dto.CouponResDTO;
import com.tibame.group1.admin.service.CouponService;
import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.common.dto.admin.CouponCreateReqDTO;
import com.tibame.group1.common.dto.admin.CouponCreateResDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.db.repository.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;


    @Override
    public CouponCreateResDTO couponCreate(CouponCreateReqDTO req) throws DateException {
        CouponEntity coupon = new CouponEntity();
        coupon.setTitle(req.getTitle());
        coupon.setLowPrice(Integer.valueOf(req.getLowPrice()));
        coupon.setDiscount(Integer.valueOf(req.getDiscount()));
        coupon.setNumber(Integer.valueOf(req.getNumber()));
        coupon.setDateStart(DateUtils.stringToDate(req.getDateStart(), DateUtils.DEFAULT_WEB_DATE_FORMAT));
        coupon.setDateEnd(DateUtils.stringToDate(req.getDateEnd(), DateUtils.DEFAULT_WEB_DATE_FORMAT));
        coupon.setAddable(Integer.valueOf(req.getAddable()));
        coupon.setLivemode(Integer.valueOf(req.getLivemode()));

        coupon = couponRepository.save(coupon);
        CouponCreateResDTO res = new CouponCreateResDTO();
        res.setCouponId(String.valueOf(coupon.getCouponID()));
        return res;
    }


    public CouponResDTO couponAll(CouponAllReqDTO req, Pageable pageable) throws DateException {
        CouponEntity exampleEntity = new CouponEntity();
        exampleEntity.setCouponID(Integer.valueOf(req.getCouponId()));
        exampleEntity.setTitle(req.getTitle());

        // 使用ExampleMatcher定義匹配規則，這裡使用默認的匹配器，即全匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 將範例對象和匹配規則組合成Example對象
        Example<CouponEntity> example = Example.of(exampleEntity, matcher);

        // 調用findAll方法進行查詢和分頁
        Page<CouponEntity> pageResult = couponRepository.findAll(example, pageable);


        // 把查詢結果從Page物件拿出來塞進去List裡面
        List<CouponAllResDTO> couponList = new ArrayList<>();
        for (CouponEntity coupon : pageResult.getContent()) {
            CouponAllResDTO resDTO = new CouponAllResDTO();
            resDTO.setCouponID(String.valueOf(coupon.getCouponID()));
            resDTO.setTitle(coupon.getTitle());
            resDTO.setLowPrice(String.valueOf(coupon.getLowPrice()));
            resDTO.setDiscount(String.valueOf(coupon.getDiscount()));
            resDTO.setNumber(String.valueOf(coupon.getNumber()));
            resDTO.setDateStart(String.valueOf(coupon.getDateStart()));
            resDTO.setDateEnd(String.valueOf(coupon.getDateEnd()));
            resDTO.setAddable(String.valueOf(coupon.getAddable()));
            resDTO.setLivemode(String.valueOf(coupon.getLivemode()));
            couponList.add(resDTO);
        }
        PagesResDTO pagesResDTO = new PagesResDTO();
        pagesResDTO.setTotalPage(String.valueOf(pageResult.getTotalPages()));
        CouponResDTO res = new CouponResDTO();
        res.setCouponList(couponList);
        res.setPages(pagesResDTO);
        return res;
    }

}

