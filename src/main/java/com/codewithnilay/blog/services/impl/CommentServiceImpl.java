package com.codewithnilay.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithnilay.blog.entities.Comment;
import com.codewithnilay.blog.entities.Post;
import com.codewithnilay.blog.exceptions.ResourceNotFoundException;
import com.codewithnilay.blog.payloads.CommentDto;
import com.codewithnilay.blog.repositories.CommentRepo;
import com.codewithnilay.blog.repositories.PostRepo;
import com.codewithnilay.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {

	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
	Post post=this.postRepo.findById(postId)
			.orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
	
	Comment comments=this.modelMapper.map(commentDto, Comment.class);
		
		comments.setPost(post);
		
		Comment savedComment=this.commentRepo.save(comments);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment","comment Id",commentId));
		
		this.commentRepo.delete(com);
	}

}
