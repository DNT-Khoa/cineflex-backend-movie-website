package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.PostRequest;
import com.khoa.CineFlex.DTO.PostResponse;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.CategoryMapper;
import com.khoa.CineFlex.mapper.PostMapper;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.model.Post;
import com.khoa.CineFlex.repository.CategoryRepository;
import com.khoa.CineFlex.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PostMapper postMapper;

    @BeforeEach
    public void setup() {
        postService = new PostService(postRepository, categoryMapper, categoryRepository, postMapper);
    }

    @Test
    void getAllPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, null));
        posts.add(new Post((long)2, "Post 2", "backdropImage", "content", Instant.now(), 100, null));

        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse((long)1, "Post 1", null, "backdropImage", "content", Instant.now()));
        postResponses.add(new PostResponse((long)2, "Post 2", null, "backdropImage", "content", Instant.now()));

        Mockito.when(postRepository.findAll()).thenReturn(posts);
        Mockito.when(postMapper.listPostToListPostResponse(posts)).thenReturn(postResponses);

        List<PostResponse> actualPostResponse = postService.getAllPosts();

        Assertions.assertThat(actualPostResponse.size()).isEqualTo(postResponses.size());
        Assertions.assertThat(actualPostResponse.get(0)).isEqualTo(postResponses.get(0));
        Assertions.assertThat(actualPostResponse.get(1)).isEqualTo(postResponses.get(1));
    }

    @Test
    void getAllPostsByCategory() {
        Category category = new Category((long)1, "category 1", null, new ArrayList<>());
        category.getPosts().add(new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, null));

        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse((long)1, "Post 1", null, "backdropImage", "content", Instant.now()));

        Mockito.when(categoryRepository.findById((long)1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(postMapper.listPostToListPostResponse(Mockito.any())).thenReturn(postResponses);

        List<PostResponse> actualPostResponses = postService.getAllPostsByCategory((long)1);
        Assertions.assertThat(actualPostResponses.size()).isEqualTo(postResponses.size());
        Assertions.assertThat(actualPostResponses.get(0)).isEqualTo(postResponses.get(0));
    }

    @Test
    void getAllPostsByCategoryThrowException() {
        Mockito.when(categoryRepository.findById((long)1)).thenThrow(new CineFlexException("Cannot find category with id: 1"));

        Assertions.assertThatThrownBy(() -> {
            postService.getAllPostsByCategory((long)1);
        }).isInstanceOf(CineFlexException.class).hasMessage("Cannot find category with id: 1");
    }

    @Test
    void getCountPostPerCategory() {
        Category category = new Category((long)1, "category 1", null, new ArrayList<>());

        Mockito.when(categoryRepository.findById((long)1)).thenReturn(java.util.Optional.of(category));

        Assertions.assertThat(postService.getCountPostPerCategory((long)1)).isEqualTo(0);
    }

    @Test
    void searchPostByKey() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, null));
        posts.add(new Post((long)2, "Post 2", "backdropImage", "content", Instant.now(), 100, null));

        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse((long)1, "Post 1", null, "backdropImage", "content", Instant.now()));
        postResponses.add(new PostResponse((long)2, "Post 2", null, "backdropImage", "content", Instant.now()));

        Mockito.when(postRepository.searchPostByKey("key")).thenReturn(posts);
        Mockito.when(postMapper.listPostToListPostResponse(posts)).thenReturn(postResponses);

        List<PostResponse> actualPostResponse = postService.searchPostByKey("key");
        Assertions.assertThat(actualPostResponse.size()).isEqualTo(postResponses.size());
        Assertions.assertThat(actualPostResponse.get(0)).isEqualTo(postResponses.get(0));
        Assertions.assertThat(actualPostResponse.get(1)).isEqualTo(postResponses.get(1));
    }

    @Test
    void getPostById() {
        Post post = new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, null);

        PostResponse postResponse = new PostResponse((long)1, "Post 1", null, "backdropImage", "content", Instant.now());

        Mockito.when(postRepository.findById((long)1)).thenReturn(java.util.Optional.of(post));
        Mockito.when(postMapper.postToPostResponse(post)).thenReturn(postResponse);

        PostResponse actualPostResponse = postService.getPostById((long)1);

        Assertions.assertThat(postResponse).isEqualTo(actualPostResponse);
        Assertions.assertThat(postResponse.getTitle()).isEqualTo(actualPostResponse.getTitle());
        Assertions.assertThat(postResponse.getContent()).isEqualTo(actualPostResponse.getContent());
    }

    @Test
    void getFourTopNews() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post((long)1, "Post 1", null, "content", Instant.now(), 100, null));

        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(new PostResponse((long)1, "Post 1", null, "backdropImage", "content", Instant.now()));

        Mockito.when(postRepository.getFourTopNews()).thenReturn(posts);
        Mockito.when(postMapper.listPostToListPostResponse(posts)).thenReturn(postResponses);

        List<PostResponse> actualPostResponses = postService.getFourTopNews();

        Assertions.assertThat(actualPostResponses.size()).isEqualTo(postResponses.size());
        Assertions.assertThat(actualPostResponses.get(0)).isEqualTo(postResponses.get(0));
    }

    @Test
    void createPost() {
        PostRequest postRequest = new PostRequest("Post 1", new ArrayList<>(), "backdropImage", "content");
        postService.createPost(postRequest);

        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any(Post.class));
    }

    @Test
    void editPost() {
        PostRequest postRequest = new PostRequest("Post 1", new ArrayList<>(), "backdropImage", "content");
        Post post = new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, new ArrayList<>());

        Mockito.when(postRepository.findById((long)1)).thenReturn(java.util.Optional.of(post));
        postService.editPost((long)1, postRequest);
        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any(Post.class));
    }

    @Test
    void updateView() {
        Post post = new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, new ArrayList<>());

        Mockito.when(postRepository.findById((long)1)).thenReturn(java.util.Optional.of(post));
        postService.updateView((long)1);

        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any(Post.class));
    }

    @Test
    void deletePost() {
        Post post = new Post((long)1, "Post 1", "backdropImage", "content", Instant.now(), 100, new ArrayList<>());

        Mockito.when(postRepository.findById((long)1)).thenReturn(java.util.Optional.of(post));

        postService.deletePost((long)1);
        Mockito.verify(postRepository, Mockito.times(1)).deleteById((long)1);
    }
}
