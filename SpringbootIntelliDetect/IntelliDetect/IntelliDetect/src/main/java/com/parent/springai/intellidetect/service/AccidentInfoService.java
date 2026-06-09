package com.parent.springai.intellidetect.service;

import com.parent.springai.intellidetect.dto.AccidentInfoDTO;
import com.parent.springai.intellidetect.dto.CreateAccidentRequest;
import com.parent.springai.intellidetect.dto.PageResponse;
import com.parent.springai.intellidetect.dto.UpdateDisplayRequest;
import com.parent.springai.intellidetect.entity.AccidentInfo;
import com.parent.springai.intellidetect.repository.AccidentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccidentInfoService {

    @Autowired
    private AccidentInfoRepository accidentInfoRepository;
    @Autowired
    private IoTDAService ioTDAService;

    public AccidentInfoDTO createAccident(CreateAccidentRequest request) {
        AccidentInfo accidentInfo = new AccidentInfo();
        accidentInfo.setVideoUrl(request.getVideoUrl());
        accidentInfo.setImageUrl(request.getImageUrl());
        accidentInfo.setAccidentDescription(request.getAccidentDescription());
        accidentInfo.setAccidentDescriptionText(request.getAccidentDescriptionText());
        accidentInfo.setAccidentDescriptionTime(request.getAccidentDescriptionTime());
        accidentInfo.setAccidentDescriptionState(request.getAccidentDescriptionState());
        accidentInfo.setAccidentX(request.getAccidentX());
        accidentInfo.setAccidentY(request.getAccidentY());

        AccidentInfo saved = accidentInfoRepository.save(accidentInfo);
        // 向IoTDA设备发布命令2
        ioTDAService.sendAccidentWarning(2);
        return convertToDTO(saved);
    }

    public AccidentInfoDTO updateDisplayInfo(Long id, UpdateDisplayRequest request) {
        Optional<AccidentInfo> optional = accidentInfoRepository.findById(id);
        if (optional.isPresent()) {
            AccidentInfo accidentInfo = optional.get();
            accidentInfo.setDisplayInfo(request.getDisplayInfo());
            AccidentInfo updated = accidentInfoRepository.save(accidentInfo);
            return convertToDTO(updated);
        }
        return null;
    }

    public PageResponse<AccidentInfoDTO> getAccidentList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AccidentInfo> accidentPage = accidentInfoRepository.findAll(pageable);

        List<AccidentInfoDTO> records = accidentPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(
                accidentPage.getTotalElements(),
                records,
                page,
                size,
                accidentPage.getTotalPages());
    }

    public AccidentInfoDTO getAccidentById(Long id) {
        Optional<AccidentInfo> optional = accidentInfoRepository.findById(id);
        return optional.map(this::convertToDTO).orElse(null);
    }

    private AccidentInfoDTO convertToDTO(AccidentInfo accidentInfo) {
        return new AccidentInfoDTO(
                accidentInfo.getId(),
                accidentInfo.getVideoUrl(),
                accidentInfo.getImageUrl(),
                accidentInfo.getAccidentDescription(),
                accidentInfo.getAccidentDescriptionText(),
                accidentInfo.getAccidentDescriptionTime(),
                accidentInfo.getAccidentDescriptionState(),
                accidentInfo.getAccidentX(),
                accidentInfo.getAccidentY());
    }
}
