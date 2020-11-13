package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.config.JwtAuthenticationEntryPoint;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.service.JwtUtil;
import com.khoa.CineFlex.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    public void getAllMovies() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getAllMovies()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void getCountPostPerCategory() throws Exception {
        Mockito.when(this.movieService.getCountMoviePerCategory(1L)).thenReturn(5);

        this.mockMvc.perform(get("/api/movies/count").param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    public void getAllMoviesByCategory() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getAllMoviesByCategory(1L)).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/byCategory").param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void checkIfMovieExistsInDatabase() throws Exception {
        Mockito.when(this.movieService.checkIfMovieExitsInDatabase(2000L)).thenReturn(true);

        this.mockMvc.perform(get("/api/movies/checkExists/2000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void getAllMoviesLimit4() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getAllMoviesLimit4()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/all/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void getMovieByTmdbId() throws Exception {
        MovieDto movieDto = new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>());

        Mockito.when(this.movieService.getMovieByTmdbId(2000L)).thenReturn(movieDto);

        this.mockMvc.perform(get("/api/movies/2000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Movie 1"))
                .andExpect(jsonPath("$.tmdbId").value(2000))
                .andExpect(jsonPath("$.posterLink").value("posterLink"));
    }

    @Test
    public void getAllComingMovies() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getAllComingMovies()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/comingsoon"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void getFourLatestComingSoonMovies() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getFourLatestComingSoonMovies()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/comingsoon/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void getAllNowPlayingMovies() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getAllNowPlayingMovies()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/nowplaying"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void getFourLatestNowPlayingMovies() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getFourLatestNowPlayingMovies()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/nowplaying/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void getTopRatedMovies() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getTopRatedMovies()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/toprated"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void getTopRatedMoviesLimit4() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.getTopRatedMoviesLimit4()).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/toprated/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }

    @Test
    public void searchMoviesByQueryKey() throws Exception {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MovieDto(1L, 2000L, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(this.movieService.searchMovieByQueryKey("key")).thenReturn(movieDtoList);

        this.mockMvc.perform(get("/api/movies/search").param("key", "key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tmdbId").value(2000))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].posterLink").value("posterLink"));
    }
}
