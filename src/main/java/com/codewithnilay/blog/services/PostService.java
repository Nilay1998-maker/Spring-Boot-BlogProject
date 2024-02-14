package com.codewithnilay.blog.services;

import java.util.List;

import com.codewithnilay.blog.entities.Post;
import com.codewithnilay.blog.payloads.PostDto;
import com.codewithnilay.blog.payloads.PostResponse;

public interface PostService {

	// create
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	// update post
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	// delete
	
	void deletePost(Integer postId);
	
    // getAll Post
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortby,String sortDir);
	
	// get single post
	PostDto getPostById(Integer postId);
	
	// get post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// get post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	// search post
	List<PostDto> searchPosts(String keywords);
	
	
}
