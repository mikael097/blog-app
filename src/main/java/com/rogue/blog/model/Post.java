package com.rogue.blog.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
/*
  User uniqueConstraints if you want to have composite unique constraint.
  else for column constraint use in @Column
*/
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "content", nullable = false)
  private String content;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
  private Set<Comments> comments = new HashSet<>();

}
