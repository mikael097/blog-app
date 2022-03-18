package com.rogue.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommentResponse {
  private final List<CommentDto> comments;
  private final int pageNo;
  private final int pageSize;
  private final long totalElements;
  private final int totalPages;
  private final boolean isLast;
}
