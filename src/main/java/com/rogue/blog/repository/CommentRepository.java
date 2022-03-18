package com.rogue.blog.repository;

import com.rogue.blog.model.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
  Comments findCommentsById(Long commentId);
  Page<Comments> findAllByPostId(Long postId, Pageable pageable);
}
