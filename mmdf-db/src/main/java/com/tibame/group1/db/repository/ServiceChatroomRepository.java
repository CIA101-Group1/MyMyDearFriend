package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ServiceChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface ServiceChatroomRepository extends JpaRepository<ServiceChatroomEntity , Integer> {
    @Query("SELECT message FROM ServiceChatroomEntity WHERE customerId = ?1  ORDER BY date DESC LIMIT 1")
    String findLastMessage(Integer id);

    @Query("SELECT m FROM ServiceChatroomEntity m WHERE m.customerId = ?1 AND m.serviceId = ?2")
    List<ServiceChatroomEntity> findHistoryMessage(Integer member, Integer service);

    //ID查詢
    @Query("SELECT m FROM ServiceChatroomEntity m WHERE m.customerId = ?1")
    List<ServiceChatroomEntity> findHistoryCustomer(Integer customerId);

    //時間查詢
    @Query("SELECT m FROM ServiceChatroomEntity m WHERE DATE(m.date) = DATE(?1)")
    List<ServiceChatroomEntity> findHistoryCustomer(String date);

//    @Query("SELECT m FROM ServiceChatroomEntity m WHERE m.customerId = ?1 AND DATE(m.date) = DATE(?2)")
//    List<ServiceChatroomEntity> findHistoryCustomer(Integer memberId,String date);

    //指定時間和ID查詢
    @Query("SELECT m FROM ServiceChatroomEntity m WHERE m.customerId = ?1 AND (DATE(m.date) > DATE(?2) AND DATE(m.date) < DATE(?3))")
    List<ServiceChatroomEntity> findHistoryCustomer(Integer memberId,String dateStar ,String dateEnd);

    //指定年分月份查詢
    @Query("SELECT m FROM ServiceChatroomEntity m WHERE YEAR(m.date) = ?1 AND MONTH(m.date) = ?2 ")
    List<ServiceChatroomEntity> findHistoryCustomer(Integer year, Integer month);

    @Query("SELECT m FROM ServiceChatroomEntity m WHERE m.customerId = ?1 AND (YEAR(m.date) = ?2 AND MONTH(m.date) = ?3) ")
    List<ServiceChatroomEntity> findHistoryCustomer(Integer memberId, Integer year, Integer month);




}
