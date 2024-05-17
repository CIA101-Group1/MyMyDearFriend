package com.tibame.group1.admin.service.Impl;

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
    public List<MarketDetailResDTO> marketDetail(AdminLoginSourceDTO adminLoginSource) {
        List<MarketDetailResDTO> detailResDTOS = new ArrayList<>();
        List<MarketEntity> markets = marketRepository.findAll();
        for (MarketEntity market : markets) {
            MarketDetailResDTO resDTO = new MarketDetailResDTO();
            resDTO.setMarketName(market.getMarketName());
            resDTO.setMarketDescription(market.getMarketDescription());
            resDTO.setMarketLocation(market.getMarketLocation());
            resDTO.setMarketStart(DateUtils.dateToSting(market.getMarketStart(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setMarketEnd(DateUtils.dateToSting(market.getMarketEnd(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setMarketFee(market.getMarketFee());
            //計算報名人數待補
            resDTO.setApplicantLimit(market.getApplicantLimit());
            resDTO.setStartDate(DateUtils.dateToSting(market.getStartDate(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setEndDate(DateUtils.dateToSting(market.getEndDate(), DateUtils.DEFAULT_DATE_FORMAT));
            resDTO.setMarketImage(
                    null == market.getMarketImage() ? null : ConvertUtils.bytesToBase64(market.getMarketImage()));
            resDTO.setStatus(MarketDetailResDTO.Status.QUERY_SUCCESS.getCode());
            detailResDTOS.add(resDTO);

        }
        return detailResDTOS;

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
}
