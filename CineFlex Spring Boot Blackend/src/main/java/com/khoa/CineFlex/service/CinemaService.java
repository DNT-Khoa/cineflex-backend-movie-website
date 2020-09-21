package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CinemaDto;
import com.khoa.CineFlex.exception.CinemaNotFoundException;
import com.khoa.CineFlex.mapper.CinemaMapper;
import com.khoa.CineFlex.model.Cinema;
import com.khoa.CineFlex.repository.CinemaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    @Transactional
    public CinemaDto createCinema(CinemaDto cinemaDto) {
        Cinema cinema = cinemaMapper.cinemaDtoToCinema(cinemaDto);

        Cinema save = cinemaRepository.save(cinema);

        return cinemaMapper.cinemaToDto(save);
    }

    @Transactional(readOnly = true)
    public CinemaDto getCinemaById(Long id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(()-> new CinemaNotFoundException("Cannot find cinema with id " + id));
        return cinemaMapper.cinemaToDto(cinema);
    }

    @Transactional
    public void deleteCinemaById(Long id) {
        cinemaRepository.deleteById(id);
    }
}
