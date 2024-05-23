package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.service.MarketService;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.common.utils.FileUtils;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.entity.MarketEntity;
import com.tibame.group1.db.repository.MarketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MarketServiceImpl implements MarketService {

    @Autowired
    MarketRepository marketRepository;

    /**
     * 後台新增活動
     */
    public MarketCreateResDTO marketCreate(MarketCreateReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException, DateException {
        MarketEntity market = new MarketEntity();
        MarketCreateResDTO resDTO = new MarketCreateResDTO();
        //確認用戶身分是否有效
        if (adminLoginSource == null) {
            //如果登入信息不完整，拋出異常或返回錯誤提示
            throw new CheckRequestErrorException("登入異常");
        }
        market.setMarketName(req.getMarketName());
        market.setMarketDescription(req.getMarketDescription());
        market.setMarketLocation(req.getMarketLocation());
        market.setMarketStart(DateUtils.stringToDate(req.getMarketStart(), DateUtils.DEFAULT_DATE_FORMAT));
        market.setMarketEnd(DateUtils.stringToDate(req.getMarketEnd(), DateUtils.DEFAULT_DATE_FORMAT));
        market.setMarketFee(req.getMarketFee());
        market.setApplicantLimit(req.getApplicantLimit());
        market.setStartDate(DateUtils.stringToDate(req.getStartDate(), DateUtils.DEFAULT_DATE_FORMAT));
        market.setEndDate(DateUtils.stringToDate(req.getEndDate(), DateUtils.DEFAULT_DATE_FORMAT));
        market.setMarketStatus(1);
        if (!StringUtils.isEmpty(req.getMarketImage())) {
            if (!FileUtils.ImageFormatChecker(req.getMarketImage().split(",")[1])) {
                resDTO.setStatus(MarketCreateResDTO.Status.IMAGE_FORMAT_ERROR.getCode());
                return resDTO;
            } else {
                market.setMarketImage(ConvertUtils.base64ToBytes(req.getMarketImage().split(",")[1]));
            }
        }
        market = marketRepository.save(market);
        resDTO.setMarketId(String.valueOf(market.getMarketId()));
        resDTO.setStatus(MarketCreateResDTO.Status.CREATE_SUCCESS.getCode());
        return resDTO;


    }

    /**
     * 後台查詢市集活動資料
     */

    @Override
    public MarketDetailResDTO marketDetailById(AdminLoginSourceDTO adminLoginSource, Integer marketId)
    throws CheckRequestErrorException{
        MarketEntity market = marketRepository.findById(marketId).orElse(null);
        if(null == market){
            throw new CheckRequestErrorException("查無此市集資料");
        }
            MarketDetailResDTO resDTO = new MarketDetailResDTO();
        resDTO.setMarketId(market.getMarketId());
            resDTO.setMarketName(market.getMarketName());
            resDTO.setMarketDescription(market.getMarketDescription());
            resDTO.setMarketLocation(market.getMarketLocation());
            resDTO.setMarketStart(DateUtils.dateToSting(market.getMarketStart(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setMarketEnd(DateUtils.dateToSting(market.getMarketEnd(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setMarketFee(market.getMarketFee());
            resDTO.setApplicantPopulation(market.getApplicantPopulation());
            resDTO.setApplicantLimit(market.getApplicantLimit());
            resDTO.setStartDate(DateUtils.dateToSting(market.getStartDate(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setEndDate(DateUtils.dateToSting(market.getEndDate(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setMarketStatus(market.getMarketStatus());
            resDTO.setMarketImage(
                    null == market.getMarketImage() ? null : "data:image/png;base64, " + ConvertUtils.bytesToBase64(market.getMarketImage()));

        return resDTO;

    }

    /**
     * 修改市集資料
     */
    @Override
    public MarketEditResDTO marketEdit(MarketEditReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException, DateException {
        MarketEditResDTO resDTO = new MarketEditResDTO();
        //檢查用戶身分是否有效
        if (adminLoginSource == null) {
            //如果登入信息不完整，拋出異常或返回錯誤提示
            throw new CheckRequestErrorException("登入異常");
        }
        //根據市集ID查找市集資料
        Optional<MarketEntity> optionalMarket = marketRepository.findById(req.getMarketId());
        if (!optionalMarket.isPresent()) {
            throw new CheckRequestErrorException("找不到該市集資料");
        }
        //獲取MarketEntity對象
        MarketEntity market = optionalMarket.get();
        if (null != req.getMarketName()) {
           market.setMarketName(req.getMarketName());
        }
        if (null != req.getMarketDescription()) {
            market.setMarketDescription(req.getMarketDescription());
        }
        if (null != req.getMarketLocation()) {
            market.setMarketLocation(req.getMarketLocation());
        }
        if (null != req.getMarketStart()) {
            market.setMarketStart(DateUtils.stringToDate(req.getMarketStart(),DateUtils.DEFAULT_DATE_FORMAT));
        }
        if (null != req.getMarketEnd()) {
            market.setMarketEnd(DateUtils.stringToDate(req.getMarketEnd(),DateUtils.DEFAULT_DATE_FORMAT));
        }
        if (null != req.getMarketFee()) {
            market.setMarketFee(req.getMarketFee());
        }
        if (null != req.getApplicantLimit()) {
            market.setApplicantLimit(req.getApplicantLimit());
        }
        if (null != req.getStartDate()) {
            market.setStartDate(DateUtils.stringToDate(req.getStartDate(),DateUtils.DEFAULT_DATE_FORMAT));
        }
        if (null != req.getEndDate()) {
            market.setEndDate(DateUtils.stringToDate(req.getEndDate(),DateUtils.DEFAULT_DATE_FORMAT));
        }
        if (null != req.getMarketStatus()) {
            market.setMarketStatus(req.getMarketStatus());
        }
        if (null != req.getMarketImage()) {
            market.setMarketImage(
                    StringUtils.isEmpty(req.getMarketImage())
                            ? null
                            : ConvertUtils.base64ToBytes(req.getMarketImage().split(",")[1]));
        } else {
            market.setMarketImage(null);
        }
        marketRepository.save(market);
        resDTO.setStatus(MarketEditResDTO.Status.EDIT_SUCCESS.getCode());
        return resDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarketAllResDTO> marketAll(
            AdminLoginSourceDTO adminLoginSourceDTO, String marketName) {

        List<MarketEntity> markets;

        if (marketName == null || marketName.isEmpty()) {
            // 如果 marketName 为空，则直接查询所有市集活動
            markets = marketRepository.findAll();
        } else {
            // 如果 marketName 不为空，则使用 ExampleMatcher 进行匹配查询
            MarketEntity exampleEntity = new MarketEntity();
            exampleEntity.setMarketName(marketName);

            // 使用包含匹配器
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("marketName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            // 将示例对象和匹配器组合成 Example 对象
            Example<MarketEntity> example = Example.of(exampleEntity, matcher);

            // 调用 findAll 方法进行查询
            markets = marketRepository.findAll(example);
        }

        // 将查询结果转换为 MarketAllResDTO 对象
        List<MarketAllResDTO> marketList = new ArrayList<>();
        for (MarketEntity market : markets) {
            MarketAllResDTO resDTO = new MarketAllResDTO();
            resDTO.setMarketId(String.valueOf(market.getMarketId()));
            resDTO.setMarketName(market.getMarketName());
            resDTO.setMarketDescription(market.getMarketDescription());
            resDTO.setMarketLocation(market.getMarketLocation());
            resDTO.setMarketStart(DateUtils.dateToSting(market.getMarketStart()));
            resDTO.setMarketEnd(DateUtils.dateToSting(market.getMarketEnd()));
            resDTO.setMarketFee(market.getMarketFee());
            resDTO.setApplicantPopulation(market.getApplicantPopulation());
            resDTO.setApplicantLimit(market.getApplicantLimit());
            resDTO.setStartDate(DateUtils.dateToSting(market.getStartDate()));
            resDTO.setEndDate(DateUtils.dateToSting(market.getEndDate()));
            resDTO.setMarketStatus(market.getMarketStatus());
            resDTO.setMarketImage(
                    null == market.getMarketImage() ? null : "data:image/png;base64, " + ConvertUtils.bytesToBase64(market.getMarketImage()));
            marketList.add(resDTO);
        }
        return marketList;
    }
}
