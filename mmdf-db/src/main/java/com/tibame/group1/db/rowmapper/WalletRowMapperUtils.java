package com.tibame.group1.db.rowmapper;


import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletRowMapperUtils implements RowMapper<WalletHistoryEntity> {

    @Override
    public WalletHistoryEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        WalletHistoryEntity walletHistoryEntity = new WalletHistoryEntity();

        walletHistoryEntity.setWalletHistoryID(resultSet.getInt("wallet_history_id"));
        walletHistoryEntity.setChangeTime(resultSet.getTimestamp("change_time"));
//        walletHistoryEntity.setMemberID(resultSet.getInt("member_id"));
        walletHistoryEntity.setChangeAmount(resultSet.getInt("change_amount"));

        String categoryStr = resultSet.getString("change_type");
        WalletCategory category = WalletCategory.valueOf(categoryStr);
        walletHistoryEntity.setChangeType(category);

        walletHistoryEntity.setWalletAmount(resultSet.getInt("wallet_amount"));
//        walletHistoryEntity.setChangeType(WalletCategory.valueOf(resultSet.getString("change_type")));

        return walletHistoryEntity;
    }
}