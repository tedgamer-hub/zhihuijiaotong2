package com.parent.springai.intellidetect.repository;

import com.parent.springai.intellidetect.entity.Accident;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long> {

    // 统计严重事故数
    Long countByAccidentDescriptionState(String state);

    // 统计今日新增事故数
    @Query("SELECT COUNT(a) FROM Accident a WHERE a.accidentDescriptionTime BETWEEN :startTime AND :endTime")
    Long countByAccidentDescriptionTimeBetween(@Param("startTime") String startTime,
                                               @Param("endTime") String endTime);
    
    // 获取最新的事故信息
    @Query("SELECT a FROM Accident a ORDER BY a.accidentDescriptionTime DESC")
    List<Accident> findLatestAccident(Pageable pageable);
}