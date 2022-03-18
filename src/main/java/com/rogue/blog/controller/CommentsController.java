package com.rogue.blog.controller;

import com.rogue.blog.dtos.CommentDto;
import com.rogue.blog.dtos.CommentResponse;
import com.rogue.blog.service.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rogue.blog.constants.RequestParameters.*;
import static com.rogue.blog.constants.RequestParameters.DEFAULT_SORT_DIR;

@RestController
@RequestMapping("/api/")
public class CommentsController {
  private final ICommentService commentService;

  public CommentsController(ICommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  @GetMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentResponse> getCommentsByPostId(
          @PathVariable Long postId,
          @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
          @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir) {

    return new ResponseEntity<>(commentService.getAllCommentsByPostId(postId, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
  }

  @GetMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> getCommentsByCommentId(@PathVariable Long postId, @PathVariable(value = "id") Long commentId) {
    return new ResponseEntity<>(commentService.getCommentByCommentId(postId, commentId), HttpStatus.OK);
  }

  @PutMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> updateCommentsById
          (@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long commentId, @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.updateCommentByCommentId(postId, commentId, commentDto), HttpStatus.OK);
  }

  @DeleteMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<String> deleteCommentById(@PathVariable Long postId, @PathVariable(value = "id") Long commentId) {
    return new ResponseEntity<>(commentService.deleteCommentByCommentId(postId, commentId), HttpStatus.OK);
  }

}
