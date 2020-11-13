package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.PostResponse;
import com.khoa.CineFlex.config.JwtAuthenticationEntryPoint;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.service.JwtUtil;
import com.khoa.CineFlex.service.PostService;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;



    @Test
    public void getAllPosts() throws Exception {
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse(1L, "Post 1", new ArrayList<>(), "backdropImage", "content", Instant.now()));

        Mockito.when(this.postService.getAllPosts()).thenReturn(postResponses);

        this.mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[0].backdropImage").value("backdropImage"))
                .andExpect(jsonPath("$[0].content").value("content"));
    }

    @Test
    public void getAllPostsByCategory() throws Exception {
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse(1L, "Post 1", new ArrayList<>(), "backdropImage", "content", Instant.now()));

        Mockito.when(this.postService.getAllPostsByCategory(2L)).thenReturn(postResponses);

        this.mockMvc.perform(get("/api/posts/byCategory").param("categoryId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[0].backdropImage").value("backdropImage"))
                .andExpect(jsonPath("$[0].content").value("content"));
    }

    @Test
    public void getCountPostPerCategory() throws Exception {
        Mockito.when(this.postService.getCountPostPerCategory(2L)).thenReturn(5);

        this.mockMvc.perform(get("/api/posts/count").param("categoryId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    public void searchPostByKey() throws Exception {
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse(1L, "Post 1", new ArrayList<>(), "backdropImage", "content", Instant.now()));

        Mockito.when(this.postService.searchPostByKey("key")).thenReturn(postResponses);

        this.mockMvc.perform(get("/api/posts/search").param("key", "key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[0].backdropImage").value("backdropImage"))
                .andExpect(jsonPath("$[0].content").value("content"));
    }

    @Test
    public void getPostById() throws Exception {
        PostResponse postResponse = new PostResponse(1L, "Post 1", new ArrayList<>(), "backdropImage", "content", Instant.now());

        Mockito.when(this.postService.getPostById(1L)).thenReturn(postResponse);

        this.mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Post 1"))
                .andExpect(jsonPath("$.backdropImage").value("backdropImage"))
                .andExpect(jsonPath("$.content").value("content"));
    }

    @Test
    public void getFourTopNews() throws Exception {
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse(1L, "Post 1", new ArrayList<>(), "backdropImage", "content", Instant.now()));

        Mockito.when(this.postService.getFourTopNews()).thenReturn(postResponses);

        this.mockMvc.perform(get("/api/posts/top/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[0].backdropImage").value("backdropImage"))
                .andExpect(jsonPath("$[0].content").value("content"));
    }


    @Test
    public void updateView() throws Exception {
        this.mockMvc.perform(put("/api/posts/updateView/1"))
                .andExpect(status().isOk());
    }

}
