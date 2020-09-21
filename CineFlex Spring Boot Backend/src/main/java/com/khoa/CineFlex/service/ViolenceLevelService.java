package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.ViolenceLevelDto;
import com.khoa.CineFlex.mapper.ViolenceLevelMapper;
import com.khoa.CineFlex.model.ViolenceLevel;
import com.khoa.CineFlex.repository.ViolenceLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ViolenceLevelService {
    private final ViolenceLevelRepository violenceLevelRepository;
    private final ViolenceLevelMapper violenceLevelMapper;

    @Transactional
    public ViolenceLevelDto createViolenceLevel(ViolenceLevelDto violenceLevelDto) {
        ViolenceLevel violenceLevel = violenceLevelMapper.violenceLevelDtoToViolenceLevel(violenceLevelDto);

        ViolenceLevel save = violenceLevelRepository.save(violenceLevel);
        return violenceLevelMapper.violenceLevelToDto(save);
    }

    @Transactional
    public void deleteViolenceLevel(Long violenceLevelId) {
        violenceLevelRepository.deleteById(violenceLevelId);
    }
}
