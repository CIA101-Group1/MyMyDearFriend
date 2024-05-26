package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.AIMessageResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AIMessageResponseRepository extends JpaRepository<AIMessageResponseEntity,Integer> {

    @Query("SELECT ai.answer FROM AIMessageResponseEntity ai WHERE ai.question = :question")
    Optional<String> findAnswer(@Param("question") String question);
}
