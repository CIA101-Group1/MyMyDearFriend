package com.tibame.group1.db.dao.impl;

import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.db.dao.WalletHistoryDAO;
import com.tibame.group1.db.dto.WalletReqDTO;
import com.tibame.group1.db.rowmapper.WalletRowMapperUtils;
import com.tibame.group1.db.entity.WalletHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WalletHistoryDAOImpl implements WalletHistoryDAO {

  @Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public WalletHistoryEntity getWalletHistoryById(Integer walletHistoryId) {
    String sql =
        "SELECT wallet_history_id, change_time, member_id, change_amount, change_type\n"
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
  public Integer createWalletHistory(WalletReqDTO walletReqDTO) {

    String sql =
        "INSERT INTO wallet_history (change_time, member_id, change_amount, change_type) "
            + "VALUES (:changeTime, :memberID, :changeAmount, :changeType)";

    Map<String, Object> map = new HashMap<>();
    map.put("memberID", walletReqDTO.getMemberID());
    map.put("changeAmount", walletReqDTO.getChangeAmount());
    map.put("changeType", walletReqDTO.getChangeType().toString());

    Date now = new Date();
    map.put("changeTime", now);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

    int walletID = keyHolder.getKey().intValue();

    return walletID;
  }

  @Override
  public List<WalletHistoryEntity> getWallets(WalletCategory walletCategory) {
    String sql =
        "SELECT wallet_history_id, change_time, member_id, change_amount, change_type "
            + "FROM wallet_history WHERE 1=1";

    Map<String, Object> map = new HashMap<>();

    if (walletCategory != null) {
      sql = sql + " AND change_type = :changeType";
      map.put("changeType", walletCategory.name());
    }

    List<WalletHistoryEntity> walletHistoryEntityList =
        namedParameterJdbcTemplate.query(sql, map, new WalletRowMapperUtils());

    return walletHistoryEntityList;
  }
}
