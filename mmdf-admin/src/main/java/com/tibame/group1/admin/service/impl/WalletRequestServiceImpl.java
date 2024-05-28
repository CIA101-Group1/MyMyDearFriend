package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.WalletAllResDTO;
import com.tibame.group1.admin.dto.WalletResDTO;
import com.tibame.group1.admin.service.WalletRequestService;
import com.tibame.group1.admin.dto.WalletReqDTO;
import com.tibame.group1.common.dto.PagesResDTO;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.WalletRequestEntity;
import com.tibame.group1.db.repository.WalletRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletRequestServiceImpl implements WalletRequestService {

  @Autowired private WalletRequestRepository walletRequestRepository;

  /**
   * 後台超級管理員查詢提領申請資料
   *
   * @param req 查詢條件
   * @param pageable 分頁資訊
   * @return 查詢結果
   */
  @Override
  public WalletAllResDTO walletAll(WalletReqDTO req, Pageable pageable) {
    WalletAllResDTO walletAllResDTO = new WalletAllResDTO();

    Page<WalletRequestEntity> pageResult;

    WalletRequestEntity filterEntity = new WalletRequestEntity();
    filterEntity.setStatus(req.getStatus());
    ExampleMatcher filterMatcher = ExampleMatcher.matching();
    // 將範例對象和匹配規則組合成Example對象
    Example<WalletRequestEntity> filterExample = Example.of(filterEntity, filterMatcher);
    // 調用findAll方法進行查詢和分頁
    pageResult = walletRequestRepository.findAll(filterExample, pageable);

    // 把查詢結果從Page物件拿出來塞進去List裡面
    List<WalletResDTO> walletRequestList = new ArrayList<>();
    for (WalletRequestEntity walletRequestEntity : pageResult.getContent()) {
      WalletResDTO walletResDTO = new WalletResDTO();
      MemberEntity member = walletRequestEntity.getMember();
      walletResDTO.setMemberId(String.valueOf(member.getMemberId()));
      walletResDTO.setStatus(walletRequestEntity.getStatus());
      walletResDTO.setWalletRequestId(walletRequestEntity.getWalletRequestId());
      walletResDTO.setAccount(walletRequestEntity.getAccount());
      walletResDTO.setRequestDate(String.valueOf(walletRequestEntity.getRequestDate()));
      walletResDTO.setDoneDate(String.valueOf(walletRequestEntity.getDoneDate()));
      walletRequestList.add(walletResDTO);
    }
    PagesResDTO pagesResDTO = new PagesResDTO();
    pagesResDTO.setTotalPages(pageResult.getTotalPages());
    pagesResDTO.setTotalCount((int) pageResult.getTotalElements());
    walletAllResDTO.setWalletRequestList(walletRequestList);
    walletAllResDTO.setPages(pagesResDTO);
    walletAllResDTO.setStatus(WalletAllResDTO.Status.REQUEST_SUCCESS.getCode());

    return walletAllResDTO;
  }
}
