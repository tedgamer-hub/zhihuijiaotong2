package com.parent.springai.intellidetect.controller;

import com.parent.springai.intellidetect.dto.AccidentStatsDTO;
import com.parent.springai.intellidetect.entity.Accident;
import com.parent.springai.intellidetect.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/accidents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccidentController {

    private final AccidentService accidentService;

    // 统一响应格式方法
    private ResponseEntity<Map<String, Object>> createResponse(Integer code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    /**
     *   创建事故
     */

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAccident(@RequestBody Accident accident) {
        try {
            // 设置事故描述时间为调用接口时的当前时间
            accident.setAccidentDescriptionTime(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            Accident createdAccident = accidentService.createAccident(accident);
            Map<String, Object> data = new HashMap<>();
            data.put("id", createdAccident.getId());
            return createResponse(200, "创建成功", data);
        } catch (Exception e) {
            return createResponse(500, "创建失败: " + e.getMessage(), null);
        }
    }

    // 识别新事故接口
    @GetMapping("/check-new")
    public ResponseEntity<Map<String, Object>> checkNewAccident() {
        try {
            // 检查最近5分钟内是否有新事故（用于实时检测）
            boolean hasRecentAccidents = accidentService.hasRecentAccidents();
            
            if (hasRecentAccidents) {
                return createResponse(200, "有新事故产生", 1);
            } else {
                return createResponse(200, "无新事故", 0);
            }
        } catch (Exception e) {
            return createResponse(500, "检查失败: " + e.getMessage(), null);
        }
    }

    // 获取事故信息列表（分页）
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAccidents(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10000") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Accident> accidentPage = accidentService.getAccidents(pageable);

            Map<String, Object> data = new HashMap<>();
            data.put("total", accidentPage.getTotalElements());
            data.put("records", accidentPage.getContent());
            data.put("page", page);
            data.put("size", size);
            data.put("totalPages", accidentPage.getTotalPages());

            return createResponse(200, "查询成功", data);
        } catch (Exception e) {
            return createResponse(500, "查询失败: " + e.getMessage(), null);
        }
    }

    // 获取事故信息详情
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAccidentDetail(@PathVariable Long id) {
        try {
            Optional<Accident> accident = accidentService.getAccidentDetail(id);
            if (accident.isPresent()) {
                return createResponse(200, "查询成功", accident.get());
            } else {
                return createResponse(404, "事故信息不存在", null);
            }
        } catch (Exception e) {
            return createResponse(500, "查询失败: " + e.getMessage(), null);
        }
    }

    // 获取事故统计数据
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAccidentStats() {
        try {
            AccidentStatsDTO stats = accidentService.getAccidentStats();

            // 手动构建响应数据，避免序列化问题
            Map<String, Object> statsData = new HashMap<>();
            statsData.put("totalAccidents", stats.getTotalAccidents());
            statsData.put("severeAccidents", stats.getSevereAccidents());
            statsData.put("todayAccidents", stats.getTodayAccidents());

            return createResponse(200, "查询成功", statsData);
        } catch (Exception e) {
            System.err.println("获取统计数据失败: " + e.getMessage());
            e.printStackTrace();
            return createResponse(500, "获取统计数据失败: " + e.getMessage(), null);
        }
    }

    // 获取最新的事故信息
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestAccident() {
        try {
            List<Accident> latestAccidents = accidentService.getLatestAccident();
            if (!latestAccidents.isEmpty()) {
                return createResponse(200, "查询成功", latestAccidents);
            } else {
                return createResponse(404, "暂无事故信息", null);
            }
        } catch (Exception e) {
            System.err.println("获取最新事故信息失败: " + e.getMessage());
            e.printStackTrace();
            return createResponse(500, "获取最新事故信息失败: " + e.getMessage(), null);
        }
    }
}