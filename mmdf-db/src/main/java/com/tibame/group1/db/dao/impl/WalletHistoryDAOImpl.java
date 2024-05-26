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

    //  @Override
    //  public Integer createWalletHistory(WalletReqDTO walletReqDTO) {
    //    // 計算新的 walletAmount
    //    Integer currentWalletAmount = getCurrentWalletAmount(walletReqDTO.getMemberID());
    //    Integer newWalletAmount = calculateNewWalletAmount(currentWalletAmount,
    // walletReqDTO.getChangeAmount(), walletReqDTO.getChangeType());
    //
    //    String sql =
    //        "INSERT INTO wallet_history (member_id, change_amount, change_type, change_time,
    // wallet_amount)\n"
    //            + "SELECT wh.member_id, :changeAmount, :changeType, :changeTime,
    // m.wallet_amount\n"
    //            + "FROM wallet_history wh\n"
    //            + "INNER JOIN member m ON wh.member_id = m.member_id\n"
    //            + "WHERE wh.member_id = :memberID";
    //
    //    Map<String, Object> map = new HashMap<>();
    //    map.put("memberID", walletReqDTO.getMemberID());
    //    map.put("changeAmount", walletReqDTO.getChangeAmount());
    //    map.put("changeType", walletReqDTO.getChangeType().getCode());
    //
    //
    //
    //    Date now = new Date();
    //    map.put("changeTime", now);
    //
    //    KeyHolder keyHolder = new GeneratedKeyHolder();
    //    namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    //
    //    int walletID = keyHolder.getKey().intValue();
    //    return walletID;
    //  }
    //
    //  private Integer calculateNewWalletAmount(Integer currentWalletAmount, Integer changeAmount,
    // WalletCategory changeType) {
    //    switch (changeType) {
    //      case TOP_UP:
    //      case DEPOSIT:
    //        return currentWalletAmount + changeAmount;
    //      case PAYMENT:
    //      case WITHDRAW:
    //      case FEE:
    //      case MARKET:
    //        return currentWalletAmount - changeAmount;
    //      default:
    //        throw new IllegalArgumentException("無效的變更類型: " + changeType);
    //    }
    //  }
    //
    //  private Integer getCurrentWalletAmount(Integer memberID) {
    //    String sql = "SELECT wallet_amount FROM wallet_history WHERE member_id = :memberID ORDER
    // BY
    // change_time DESC LIMIT 1";
    //
    //    Map<String, Object> map = new HashMap<>();
    //    map.put("memberID", memberID);
    //
    //    List<Integer> walletAmounts = namedParameterJdbcTemplate.queryForList(sql, map,
    // Integer.class);
    //
    //    if (!walletAmounts.isEmpty()) {
    //      return walletAmounts.get(0);
    //    } else {
    //      return 0;
    //    }
    //  }

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
