package com.rogue.blog.service;

import com.rogue.blog.dtos.CommentDto;
import com.rogue.blog.dtos.CommentResponse;


public interface ICommentService {
  CommentDto createComment(Long postId, CommentDto commentDto);

  CommentResponse getAllCommentsByPostId(Long postId, int pageNo, int pageSize, String sortBy, String sortDir);

  CommentDto getCommentByCommentId(Long postId, Long commentId);

  CommentDto updateCommentByCommentId(Long postId, Long commentId, CommentDto comment);

  String deleteCommentByCommentId(Long postId, Long commentId);
}
