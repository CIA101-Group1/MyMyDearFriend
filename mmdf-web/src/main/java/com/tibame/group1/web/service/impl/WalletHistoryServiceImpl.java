package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.WalletRowMapper;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.web.service.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WalletHistoryServiceImpl implements WalletHistoryService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId) {
        String sql = "SELECT wallet_history_id, change_time, member_id, change_amount, change_type" +
                "FROM wallet_history WHERE wallet_history_id = :wallet_history_id";

        Map<String, Object> map = new HashMap<>();
        map.put("wallet_history_id", walletHistoryId);

        List<WalletHistoryEntity> walletHistoryEntityList = namedParameterJdbcTemplate.query(sql, map, new WalletRowMapper());

        if (walletHistoryEntityList.size() > 0) {
            return walletHistoryEntityList.get(0);
        } else {
            return null;
        }
    }
}
