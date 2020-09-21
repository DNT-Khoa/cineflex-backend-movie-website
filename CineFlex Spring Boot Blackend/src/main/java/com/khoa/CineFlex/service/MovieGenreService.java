package com.khoa.CineFlex.service;


import com.khoa.CineFlex.DTO.MovieGenreDto;
import com.khoa.CineFlex.mapper.MovieGenreMapper;
import com.khoa.CineFlex.model.MovieGenre;
import com.khoa.CineFlex.repository.MovieGenreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;
    private final MovieGenreMapper movieGenreMapper;

    @Transactional
    public MovieGenreDto createMovieGenre(MovieGenreDto movieGenreDto) {
        MovieGenre movieGenre = movieGenreMapper.movieGenreDtoToMovieGenre(movieGenreDto);

        // Save method in jpa return the created entity
        MovieGenre save = movieGenreRepository.save(movieGenre);

        return movieGenreMapper.movieGenreToDto(save);
    }

    @Transactional(readOnly = true)
    public List<MovieGenreDto> getAll() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return movieGenreMapper.listMovieGenreToListDto(movieGenreRepository.findAll());
    }

    @Transactional
    public void deleteMovieGenre(Long movieGenreId) {
        movieGenreRepository.deleteById(movieGenreId);
    }
}
