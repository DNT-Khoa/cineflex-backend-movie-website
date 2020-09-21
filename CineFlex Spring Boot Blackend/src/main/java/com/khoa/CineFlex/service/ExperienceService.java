package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.ExperienceDto;
import com.khoa.CineFlex.exception.ExperienceNotFoundException;
import com.khoa.CineFlex.mapper.ExperienceMapper;
import com.khoa.CineFlex.model.Experience;
import com.khoa.CineFlex.model.Schedule;
import com.khoa.CineFlex.repository.ExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ExperienceService {
    private final ExperienceMapper experienceMapper;
    private final ExperienceRepository experienceRepository;

    @Transactional
    public ExperienceDto createExperience(ExperienceDto experienceDto) {
        Experience experience = experienceMapper.experienceDtoToExperience(experienceDto);

        Experience save = experienceRepository.save(experience);

        return experienceMapper.experienceToDto(save);
    }

    @Transactional
    public void deleteExperienceById(Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId).orElseThrow(()->new ExperienceNotFoundException("Cannot find expereince wiht id " + experienceId));
        for (Schedule schedule : experience.getSchedules()) {
            schedule.setExperience(null);
        }

        experienceRepository.deleteById(experienceId);
    }
}
