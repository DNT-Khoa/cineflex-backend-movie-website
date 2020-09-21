package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.ScheduleDto;
import com.khoa.CineFlex.exception.CinemaNotFoundException;
import com.khoa.CineFlex.exception.ExperienceNotFoundException;
import com.khoa.CineFlex.exception.MovieNotFoundException;
import com.khoa.CineFlex.exception.ScheduleNotFoundException;
import com.khoa.CineFlex.mapper.ScheduleMapper;
import com.khoa.CineFlex.model.*;
import com.khoa.CineFlex.repository.CinemaRepository;
import com.khoa.CineFlex.repository.ExperienceRepository;
import com.khoa.CineFlex.repository.MovieRepository;
import com.khoa.CineFlex.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleMapper scheduleMapper;
    private final ScheduleRepository scheduleRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final ExperienceRepository experienceRepository;

    @Transactional
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.scheduleDtoToSchedule((scheduleDto));

        Movie movie = movieRepository.findById(scheduleDto.getMovieId()).orElseThrow(() -> new MovieNotFoundException("Cannot find movie with id " + scheduleDto.getMovieId()));
        schedule.setMovie(movie);
        movie.getSchedules().add(schedule);

        Cinema cinema = cinemaRepository.findById(scheduleDto.getCinemaId()).orElseThrow(()-> new CinemaNotFoundException("Cannot find cinema with id " + scheduleDto.getCinemaId()));
        schedule.setCinema(cinema);
        cinema.getSchedules().add(schedule);

        Experience experience = experienceRepository.findById(scheduleDto.getExperienceId()).orElseThrow(()-> new ExperienceNotFoundException("Cannot find experience with id " + scheduleDto.getExperienceId()));
        schedule.setExperience(experience);
        experience.getSchedules().add(schedule);

        // Create a seat allocations by default
        // Create 5 x 5 seats
        List<SeatAllocation> seatAllocationList = new ArrayList<>();

        for (char row = 'A'; row <= 'C'; row++) {
            for (int seat = 1; seat <= 3; seat++) {
                SeatAllocation seatAllocation = new SeatAllocation();
                seatAllocation.setRow(row);
                seatAllocation.setSeat(seat);
                seatAllocation.setSchedule(schedule);
                seatAllocation.setStatus(0);

                seatAllocationList.add(seatAllocation);
            }
        }
        schedule.setSeatAllocations(seatAllocationList);

        Schedule save = scheduleRepository.save(schedule);

        return scheduleMapper.scheduleToDto(save);
    }

    @Transactional(readOnly = true)
    public ScheduleDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new ScheduleNotFoundException("Cannot find schedule with id " + id));
        return scheduleMapper.scheduleToDto(schedule);
    }

    @Transactional
    public void deleteScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(()-> new ScheduleNotFoundException("Cannot find schedule with id " + scheduleId));

        if (schedule.getCinema() != null)
            schedule.getCinema().getSchedules().remove(schedule);
        if (schedule.getMovie() != null)
            schedule.getMovie().getSchedules().remove(schedule);
        if (schedule.getExperience() != null)
            schedule.getExperience().getSchedules().remove(schedule);

        scheduleRepository.deleteById(scheduleId);
    }
}
