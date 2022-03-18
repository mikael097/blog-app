package com.rogue.blog.controller;

import com.rogue.blog.dtos.PostDto;
import com.rogue.blog.dtos.PostResponse;
import com.rogue.blog.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.rogue.blog.constants.RequestParameters.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final IPostService postService;

  public PostController(IPostService postService) {
    this.postService = postService;
  }

  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPostDto(postDto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PostResponse> getAllPosts(
          @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NO) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
          @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR) String sortDir
  ) {
    return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
    return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Long id) {
    return new ResponseEntity<>(postService.updatePostById(postDto, id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePostById(@PathVariable Long id) {
    return new ResponseEntity<>(postService.deletePostById(id), HttpStatus.OK);
  }

}
