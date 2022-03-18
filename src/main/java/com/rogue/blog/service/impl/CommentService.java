package com.rogue.blog.service.impl;

import com.rogue.blog.dtos.CommentDto;
import com.rogue.blog.dtos.CommentResponse;
import com.rogue.blog.exception.ResourceNotFound;
import com.rogue.blog.model.Comments;
import com.rogue.blog.model.Post;
import com.rogue.blog.repository.CommentRepository;
import com.rogue.blog.repository.PostRepository;
import com.rogue.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction;

@Service
public class CommentService implements ICommentService {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  @Autowired
  public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
  }

  @Override
  public CommentDto createComment(Long postId, CommentDto commentDto) {
    Post post = postRepository.findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    Comments comments = Comments.builder()
            .id(commentDto.getId())
            .name(commentDto.getName())
            .body(commentDto.getBody())
            .email(commentDto.getEmail())
            .post(post)
            .build();
    Comments response = commentRepository.save(comments);
    return mapToDto(response);
  }

  @Override
  public CommentDto getCommentByCommentId(Long postId, Long commentId) {
    postRepository.findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    Comments comment = commentRepository.findCommentsById(commentId);
    return mapToDto(comment);
  }

  @Override
  public String deleteCommentByCommentId(Long postId, Long commentId) {
    postRepository.findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    Comments comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFound("Comments", "id", commentId.toString()));
    commentRepository.delete(comment);
    return String.format("Comment with id: %s deleted", commentId);
  }

  @Override
  public CommentDto updateCommentByCommentId(Long postId, Long commentId, CommentDto commentDto) {
    postRepository.findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    Comments comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFound("Comments", "id", commentId.toString()));
    comment.setName(commentDto.getName());
    comment.setBody(commentDto.getBody());
    comment.setEmail(commentDto.getEmail());
    commentRepository.save(comment);
    return mapToDto(comment);
  }

  @Override
  public CommentResponse getAllCommentsByPostId(Long postId, int pageNo, int pageSize, String sortBy, String sortDir) {
    postRepository.findPostById(postId)
            .orElseThrow(() -> new ResourceNotFound("Post", "id", postId.toString()));
    Direction direction = Direction.fromString(sortDir);
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
    Page<Comments> commentsList = commentRepository.findAllByPostId(postId, pageable);
    List<CommentDto> commentDtoList = commentsList.stream()
            .map(this::mapToDto)
            .toList();
    return CommentResponse.builder()
            .comments(commentDtoList)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalPages(commentsList.getTotalPages())
            .totalElements(commentsList.getTotalElements())
            .isLast(commentsList.isLast())
            .build();
  }

  private CommentDto mapToDto(Comments response) {
    return CommentDto.builder()
            .body(response.getBody())
            .name(response.getName())
            .email(response.getEmail())
            .id(response.getId())
            .build();
  }

}
