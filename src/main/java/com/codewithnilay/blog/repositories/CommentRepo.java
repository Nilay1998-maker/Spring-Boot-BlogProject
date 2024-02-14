package com.codewithnilay.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithnilay.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
