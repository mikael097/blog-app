package com.rogue.blog.service.impl;

import com.rogue.blog.dtos.PostDto;
import com.rogue.blog.dtos.PostResponse;
import com.rogue.blog.exception.ResourceNotFound;
import com.rogue.blog.model.Post;
import com.rogue.blog.repository.PostRepository;
import com.rogue.blog.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService {
  private final PostRepository postRepository;

  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public PostDto createPostDto(PostDto postDto) {
    Post post = mapToEntity(postDto);
    Post postResponse = postRepository.save(post);
    return mapToDto(postResponse);
  }

  @Override
  public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
    Direction direction = Direction.fromString(sortDir);
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
    Page<Post> posts = postRepository.findAll(pageable);
    List<Post> postList = posts.getContent();
    List<PostDto> content = postList.stream().map(this::mapToDto).toList();
    return PostResponse.builder()
            .pageNo(pageNo)
            .pageSize(pageSize)
            .posts(content)
            .totalPages(posts.getTotalPages())
            .totalElements(posts.getTotalElements())
            .isLast(posts.isLast())
            .build();
  }

  @Override
  public PostDto getPostById(Long postId) {
    Post post = postRepository
            .findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    return mapToDto(post);
  }

  @Override
  public PostDto updatePostById(PostDto updatedPost, Long postId) {
    Post post = postRepository
            .findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    post.setContent(updatedPost.getContent());
    post.setDescription(updatedPost.getDescription());
    post.setTitle(updatedPost.getTitle());
    Post response = postRepository.save(post);
    return mapToDto(response);
  }

  @Override
  public String deletePostById(Long postId) {
    Post post = postRepository.findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    postRepository.delete(post);
    return "Post deleted with id: " + postId;
  }

  private PostDto mapToDto(Post post) {
    return PostDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .description(post.getDescription())
            .build();
  }

  private Post mapToEntity(PostDto postDto) {
    return Post.builder()
            .title(postDto.getTitle())
            .content(postDto.getContent())
            .description(postDto.getDescription())
            .build();
  }

}
