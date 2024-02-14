package com.codewithnilay.blog.services;

import com.codewithnilay.blog.payloads.CommentDto;

public interface CommentService {
	
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);

}
