package com.rogue.blog.repository;

import com.rogue.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Optional<Post> findPostById(Long id);
}
