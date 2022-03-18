package com.rogue.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentDto {
  private final Long id;
  private final String name;
  private final String email;
  private final String body;
}
