package com.codewithnilay.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

//import org.hibernate.query.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithnilay.blog.entities.Category;
import com.codewithnilay.blog.entities.Post;
import com.codewithnilay.blog.entities.User;
import com.codewithnilay.blog.exceptions.ResourceNotFoundException;
import com.codewithnilay.blog.payloads.PostDto;
import com.codewithnilay.blog.payloads.PostResponse;
import com.codewithnilay.blog.repositories.CategoryRepo;
import com.codewithnilay.blog.repositories.PostRepo;
import com.codewithnilay.blog.repositories.UserRepo;
import com.codewithnilay.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
    private CategoryRepo categoryRepo;
	
    
    // create post
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
     
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user Id",userId));
				
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","category Id",categoryId));
		Post post=this.modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		// Example assuming Java
		post.setAddedDate(new java.util.Date());

		post.setCategory(category);
		post.setUser(user);
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	

	

// update post

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		
		Post updatedpost=this.postRepo.save(post);
		
		
		return this.modelMapper.map(updatedpost, PostDto.class);
	}
// delete post
	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
		this.postRepo.delete(post);
		
	}
// get all post
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortby,String sortDir) {
		
	//Pageable p=PageRequest.of(pageNumber, pageSize);
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortby).ascending();
		}else {
			sort=Sort.by(sortby).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber - 0, pageSize,sort);

	Page<Post> pagePost=this.postRepo.findAll(p);
	
	List<Post> allPost=pagePost.getContent();
	
	List<PostDto> postDtos=allPost.stream().
				map((post)->this.modelMapper.map(post, PostDto.class)).
		          collect(Collectors.toList());
	
	PostResponse postResponse=new PostResponse();
	postResponse.setContent(postDtos);
	postResponse.setPageNumber(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalelements(pagePost.getTotalElements());
	postResponse.setTotalPages(pagePost.getTotalPages());
	postResponse.setLastPage(pagePost.isLast());
	
	
	
		return postResponse ;
	}

	
	// get post by id
	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	// get post by category
	
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
	
		Category cat=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","category Id",categoryId));
		
		List<Post> posts=this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos=posts.stream().
				map((post)->this.modelMapper.map(post,PostDto.class)).
				collect(Collectors.toList());
		
		
		return postDtos;
	}

	// get post by user
	
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","user Id",userId));
		
		List<Post> posts=this.postRepo.findByUser(user);
		
		List<PostDto> postDtos1=posts.stream().
				map((post)->this.modelMapper.map(post,PostDto.class)).
				collect(Collectors.toList());
		              
		return postDtos1;
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		List<Post> posts= postRepo.searchByTitle("%"+keywords+"%");
		List<PostDto>postDtos=posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
