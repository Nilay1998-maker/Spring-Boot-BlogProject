package com.codewithnilay.blog.services;

import java.util.List;

import com.codewithnilay.blog.payloads.CategoryDto;

public interface CategoryService {
	
	
	// create
	
	CategoryDto createCategory(CategoryDto ategoryDto);
	
	
	// update
	CategoryDto updateCategory(CategoryDto ategoryDto,Integer categoryId);
	
	
	// delete
	void deleteCategory(Integer categoryId);
	
	// get single 
	CategoryDto getCategory(Integer categoryId);
	
	// get all 
	List<CategoryDto> getCategories();

}
