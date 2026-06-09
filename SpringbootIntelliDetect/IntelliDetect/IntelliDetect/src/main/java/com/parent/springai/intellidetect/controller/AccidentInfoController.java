package com.parent.springai.intellidetect.controller;

import com.parent.springai.intellidetect.dto.*;
import com.parent.springai.intellidetect.service.AccidentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AccidentInfoController {

    @Autowired
    private AccidentInfoService accidentInfoService;

    @PostMapping("/accidents/create-with-validation")
    public ApiResponse<AccidentInfoDTO> createAccidentWithValidation(@Valid @RequestBody CreateAccidentRequest request) {
        // 验证视频路径是否为mp4格式
        if (request.getVideoUrl() != null && !request.getVideoUrl().trim().toLowerCase().endsWith(".mp4")) {
            return ApiResponse.error(400, "你上传的视频路径有误");
        }
        
        AccidentInfoDTO result = accidentInfoService.createAccident(request);
        return ApiResponse.success("创建成功", result);
    }
    
    // 处理数据校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ApiResponse.error(400, "请完善您上传的事故记录");
    } 

    @PutMapping("/accidents/info/{id}/display")
    public ApiResponse<UpdateDisplayResponse> updateDisplayInfo(@PathVariable Long id, @RequestBody UpdateDisplayRequest request) {
        AccidentInfoDTO result = accidentInfoService.updateDisplayInfo(id, request);
        if (result != null) {
            return new ApiResponse<>(200, "更新成功", new UpdateDisplayResponse(result.getId()));
        } else {
            return ApiResponse.error(404, "事故信息不存在");
        }
    }

    @GetMapping("/accidents-info")
    public ApiResponse<PageResponse<AccidentInfoDTO>> getAccidentList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResponse<AccidentInfoDTO> result = accidentInfoService.getAccidentList(page, size);
        return ApiResponse.success(result);
    }

    @GetMapping("/accidents/info/{id}")
    public ApiResponse<AccidentInfoDTO> getAccidentById(@PathVariable Long id) {
        AccidentInfoDTO result = accidentInfoService.getAccidentById(id);
        if (result != null) {
            return ApiResponse.success(result);
        } else {
            return ApiResponse.error(404, "事故信息不存在");
        }
    }
}