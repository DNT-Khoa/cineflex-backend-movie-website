package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.DTO.PostRequest;
import com.khoa.CineFlex.DTO.PostResponse;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.CategoryMapper;
import com.khoa.CineFlex.mapper.PostMapper;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.model.Post;
import com.khoa.CineFlex.repository.CategoryRepository;
import com.khoa.CineFlex.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        List<Post> posts = this.postRepository.findAll();

        return this.postMapper.listPostToListPostResponse(posts);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryId));

        return this.postMapper.listPostToListPostResponse(category.getPosts());
    }

    @Transactional(readOnly = true)
    public int getCountPostPerCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryId));
        return category.getPosts().size();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> searchPostByKey(String key) {
        List<Post> posts = this.postRepository.searchPostByKey(key);

        return this.postMapper.listPostToListPostResponse(posts);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new CineFlexException("Cannot find post with id: " + postId));

        return this.postMapper.postToPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getFourTopNews() {
        return this.postMapper.listPostToListPostResponse(this.postRepository.getFourTopNews());
    }

    @Transactional
    public PostResponse createPost(PostRequest postRequest) {
        Post post = new Post();

        post.setTitle(postRequest.getTitle());
        post.setCategories(this.categoryMapper.listDtoToListCategory(postRequest.getCategories()));
        post.setBackdropImage(postRequest.getBackdropImage());
        post.setContent(postRequest.getContent());
        post.setCreatedAt(Instant.now());

        List<Category> categories = new ArrayList<>();
        for (CategoryDto categoryDto : postRequest.getCategories()) {
            Category category = this.categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryDto.getId()));
            category.getPosts().add(post);
            categories.add(category);
        }

        post.setCategories(categories);

        return this.postMapper.postToPostResponse(this.postRepository.save(post));
    }

    @Transactional
    public PostResponse editPost(Long postId, PostRequest postRequest) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new CineFlexException("Cannot find post with id: " + postId));

        post.setTitle(postRequest.getTitle());
        post.setBackdropImage(postRequest.getBackdropImage());
        post.setContent(postRequest.getContent());

        // Remove all references of post from categories
        for (Category category : post.getCategories()) {
            category.getPosts().remove(post);
        }

        List<Category> categories = new ArrayList<>();
        for (CategoryDto categoryDto : postRequest.getCategories()) {
            Category category = this.categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryDto.getId()));
            category.getPosts().add(post);
            categories.add(category);
        }
        post.setCategories(categories);

        return this.postMapper.postToPostResponse(this.postRepository.save(post));
    }

    @Transactional
    public void updateView(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new CineFlexException("Cannot find post with id: " + postId));
        post.setViews(post.getViews() + 1);

        this.postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new CineFlexException("Cannot find post with id: " + postId));

        // Delete all post references from other tables
        for (Category category : post.getCategories()) {
            category.getPosts().remove(post);
        }

        this.postRepository.deleteById(postId);
    }
}
