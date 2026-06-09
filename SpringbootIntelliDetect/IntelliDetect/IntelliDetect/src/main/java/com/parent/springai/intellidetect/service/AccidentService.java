package com.parent.springai.intellidetect.service;

import com.parent.springai.intellidetect.dto.AccidentStatsDTO;
import com.parent.springai.intellidetect.entity.Accident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface AccidentService {

    // 创建事故信息
    Accident createAccident(Accident accident);

    // 获取事故信息列表（分页）
    Page<Accident> getAccidents(Pageable pageable);

    // 获取事故信息详情
    Optional<Accident> getAccidentDetail(Long id);

    // 获取事故统计数据
    AccidentStatsDTO getAccidentStats();
    
    // 检查最近是否有新事故（用于实时检测）
    boolean hasRecentAccidents();
    
    // 获取最新的事故信息
    List<Accident> getLatestAccident();
}