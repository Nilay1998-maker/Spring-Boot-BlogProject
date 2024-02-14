package com.codewithnilay.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithnilay.blog.entities.Category;
import com.codewithnilay.blog.exceptions.ResourceNotFoundException;
import com.codewithnilay.blog.payloads.CategoryDto;
import com.codewithnilay.blog.repositories.CategoryRepo;
import com.codewithnilay.blog.services.CategoryService;



@Service
public class CategoryServiceImpl implements CategoryService {
     @Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto ategoryDto) {
	Category cat=this.modelMapper.map(ategoryDto, Category.class);
	Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).
				 orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		
		cat.setCategoryDexcription(categoryDto.getCategoryDexcription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		Category updatedcat=this.categoryRepo.save(cat);
	
		
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).
				 orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		 this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated
		
		Category cat=this.categoryRepo.findById(categoryId).
				 orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		
		List<Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> catDtos = categories.stream()
		        .map(cat -> modelMapper.map(cat, CategoryDto.class))
		        .collect(Collectors.toList());
		return catDtos;
	}

}
