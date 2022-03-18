package com.rogue.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class PostResponse {
  private final List<PostDto> posts;
  private final int pageNo;
  private final int pageSize;
  private final long totalElements;
  private final int totalPages;
  private final boolean isLast;
}