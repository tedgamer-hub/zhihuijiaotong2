package com.parent.springai.intellidetect.service.impl;

import com.parent.springai.intellidetect.dto.AccidentStatsDTO;
import com.parent.springai.intellidetect.entity.Accident;
import com.parent.springai.intellidetect.repository.AccidentRepository;
import com.parent.springai.intellidetect.service.AccidentService;
import com.parent.springai.intellidetect.service.IoTDAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final IoTDAService ioTDAService;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Accident createAccident(Accident accident) {
        Accident saved = accidentRepository.save(accident);
        // 向IoTDA设备发布命令2
        ioTDAService.sendAccidentWarning(2);
        return saved;
    }


    @Override
    public Page<Accident> getAccidents(Pageable pageable) {
        return accidentRepository.findAll(pageable);
    }

    @Override
    public Optional<Accident> getAccidentDetail(Long id) {
        return accidentRepository.findById(id);
    }

    @Override
    public AccidentStatsDTO getAccidentStats() {
        // 总事故数
        Long totalAccidents = accidentRepository.count();

        // 严重事故数（严重程度为"严重"的事故）
        Long severeAccidents = accidentRepository.countByAccidentDescriptionState("严重");

        // 今日新增事故数
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        Long todayAccidents = accidentRepository.countByAccidentDescriptionTimeBetween(
                startOfDay.format(TIME_FORMATTER),
                endOfDay.format(TIME_FORMATTER)
        );

        return new AccidentStatsDTO(totalAccidents, severeAccidents, todayAccidents);
    }
    
    @Override
    public boolean hasRecentAccidents() {
        // 检查最近5分钟内是否有新事故
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
        
        Long recentAccidents = accidentRepository.countByAccidentDescriptionTimeBetween(
                fiveMinutesAgo.format(TIME_FORMATTER),
                now.format(TIME_FORMATTER)
        );
        
        return recentAccidents > 0;
    }
    
    @Override
    public List<Accident> getLatestAccident() {
        // 获取最新的10条事故信息
        Pageable pageable = PageRequest.of(0, 10);
        return accidentRepository.findLatestAccident(pageable);
    }
}