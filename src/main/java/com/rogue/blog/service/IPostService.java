package com.rogue.blog.service;

import com.rogue.blog.dtos.PostDto;
import com.rogue.blog.dtos.PostResponse;

public interface IPostService {
  PostDto createPostDto(PostDto postDto);

  PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

  PostDto getPostById(Long postId);

  PostDto updatePostById(PostDto updatedPost, Long postId);

  String deletePostById(Long id);
}
