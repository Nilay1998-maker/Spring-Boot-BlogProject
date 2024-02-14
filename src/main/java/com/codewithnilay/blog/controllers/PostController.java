package com.codewithnilay.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithnilay.blog.config.AppConstants;
import com.codewithnilay.blog.payloads.ApiResponse;
import com.codewithnilay.blog.payloads.PostDto;
import com.codewithnilay.blog.payloads.PostResponse;
import com.codewithnilay.blog.services.FileService;
import com.codewithnilay.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// post mapping
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			)
	{
		  PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		  return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts=this.postService.getPostsByUser(userId);
		
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get by category
	@GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
		
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
		@RequestParam(value = "pageNumber",defaultValue=AppConstants.PAZE_NUMBER,required = false)Integer pageNumber,
		@RequestParam(value = "pageSize",defaultValue=AppConstants.PAZE_SIZE,required = false)Integer pageSize,
		@RequestParam(value = "sortby",defaultValue = AppConstants.SORT_BY,required = false) String sortby,
		@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir 
		
			){
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortby,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// get single post by post id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully",true),HttpStatus.OK);
	}
	
	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId ){
		PostDto updatepost=this.postService.updatePost(postDto, postId);
		
		
		
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
	}
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keywords){
		List<PostDto> result=this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	// post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	// method to serve file
		@GetMapping(value="/post/image/{imageName}",produces="image/jpeg")
		public void downloadImage(
			@PathVariable("imageName") String imageName,HttpServletResponse response	
				) throws FileNotFoundException {
			InputStream resource=this.fileService.getResource(path, imageName);
			response.setContentType("image/jpeg");

			
			try {
		        StreamUtils.copy(resource, response.getOutputStream());
		    } catch (IOException e) {
		        e.printStackTrace();
		        // Handle the exception, e.g., return a 500 Internal Server Error response
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    }
			
		    }
	

}
