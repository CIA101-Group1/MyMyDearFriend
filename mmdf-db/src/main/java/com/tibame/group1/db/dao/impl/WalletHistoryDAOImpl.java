package com.tibame.group1.db.dao.impl;

import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.db.dto.WalletQueryParams;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import com.tibame.group1.db.rowmapper.WalletRowMapperUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WalletHistoryDAOImpl implements WalletHistoryDAO {

    @Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId) {
        String sql =
                "SELECT wallet_history_id, change_time, member_id, change_amount, change_type, wallet_amount\n"
                        + "FROM wallet_history WHERE wallet_history_id = :wallet_history_id";

        Map<String, Object> map = new HashMap<>();
        map.put("wallet_history_id", walletHistoryId);

        List<WalletHistoryEntity> walletHistoryEntityList =
                namedParameterJdbcTemplate.query(sql, map, new WalletRowMapperUtils());

        if (walletHistoryEntityList.size() > 0) {
            return walletHistoryEntityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<WalletHistoryEntity> getWallets(WalletQueryParams walletQueryParams) {
        String sql =
                "SELECT wallet_history_id, change_time, member_id, change_amount, change_type, wallet_amount "
                        + "FROM wallet_history WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        sql = sql + " AND member_id = :memberId ";
        map.put("memberId", walletQueryParams.getMemberId());

        if (walletQueryParams.getWalletCategory() != null
                && !walletQueryParams.getWalletCategory().isEmpty()) {
            sql = sql + " AND change_type = :changeType";
            map.put("changeType", Integer.parseInt(walletQueryParams.getWalletCategory()));
        }

        if (walletQueryParams.getSearch() != null && !walletQueryParams.getSearch().isEmpty()) {
            sql = sql + " AND change_time >= :selectedTime";
            map.put("selectedTime", walletQueryParams.getSearch());
        } else {
            sql = sql + " AND change_time >= :threeMonthsAgo";
            map.put("threeMonthsAgo", calculateThreeMonthsAgo());
        }

        return namedParameterJdbcTemplate.query(sql, map, new WalletRowMapperUtils());
    }

    private Date calculateThreeMonthsAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        return calendar.getTime();
    }
}
