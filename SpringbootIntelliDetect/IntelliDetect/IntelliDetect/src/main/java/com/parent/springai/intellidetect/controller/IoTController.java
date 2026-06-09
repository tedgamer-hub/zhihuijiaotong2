package com.parent.springai.intellidetect.controller;

import com.parent.springai.intellidetect.service.IoTDAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/iot")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IoTController {
    private final IoTDAService iotdaService;

    // 统一响应格式方法
    private ResponseEntity<Map<String, Object>> createResponse(Integer code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    // 初始化IoT连接接口
    @PostMapping("/connect")
    public ResponseEntity<Map<String, Object>> connectIoT() {
        try {
            boolean connected = iotdaService.connectDevice();
            if (connected) {
                return createResponse(200, "IoT设备连接成功", null);
            } else {
                return createResponse(500, "IoT设备连接失败", null);
            }
        } catch (Exception e) {
            return createResponse(500, "IoT设备连接失败: " + e.getMessage(), null);
        }
    }

    // 发送事故警示命令接口
    @PostMapping("/accident-warning")
    public ResponseEntity<Map<String, Object>> sendAccidentWarning(@RequestBody Map<String, Integer> requestBody) {
        try {
            Integer warningLevel = requestBody.get("warningLevel");
            if (warningLevel == null) {
                return createResponse(400, "警示级别不能为空", null);
            }
            String commandId = iotdaService.sendAccidentWarning(warningLevel);
            if (commandId != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("commandId", commandId);
                return createResponse(200, "事故警示命令发送成功", data);
            } else {
                return createResponse(500, "事故警示命令发送失败", null);
            }
        } catch (IllegalArgumentException e) {
            return createResponse(400, e.getMessage(), null);
        } catch (Exception e) {
            return createResponse(500, "事故警示命令发送失败: " + e.getMessage(), null);
        }
    }

    // 接收设备消息接口
    @GetMapping("/receive")
    public ResponseEntity<Map<String, Object>> receiveDeviceMessages() {
        try {
            // 实现接收设备消息的逻辑
            // 目前华为IoT SDK可能不支持主动接收消息，需要通过MQTT订阅或回调方式实现
            return createResponse(204, "暂无新消息", null);
        } catch (Exception e) {
            return createResponse(500, "接收消息失败: " + e.getMessage(), null);
        }
    }

    // 断开IoT连接接口
    @PostMapping("/disconnect")
    public ResponseEntity<Map<String, Object>> disconnectIoT() {
        try {
            // 实现断开IoT连接的逻辑
            System.out.println("IoT设备连接已断开");
            return createResponse(200, "IoT设备连接已断开", null);
        } catch (Exception e) {
            return createResponse(500, "断开连接失败: " + e.getMessage(), null);
        }
    }

    // 接收前端数据并发送命令接口
    @PostMapping("/send-command")
    public ResponseEntity<Map<String, Object>> sendCommandFromFrontend(@RequestBody Map<String, Integer> requestBody) {
        try {
            Integer commandValue = requestBody.get("value");
            if (commandValue == null) {
                return createResponse(400, "命令值不能为空", null);
            }
            if (commandValue != 1 && commandValue != 2 && commandValue != 0) {
                return createResponse(400, "命令值必须为1、2或0", null);
            }
            String commandId = iotdaService.sendAccidentWarning(commandValue);
            if (commandId != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("commandId", commandId);
                data.put("sentValue", commandValue);
                return createResponse(200, "命令发送成功", data);
            } else {
                return createResponse(500, "命令发送失败", null);
            }
        } catch (Exception e) {
            return createResponse(500, "命令发送失败: " + e.getMessage(), null);
        }
    }
}