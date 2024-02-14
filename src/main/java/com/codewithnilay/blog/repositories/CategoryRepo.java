package com.codewithnilay.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithnilay.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
