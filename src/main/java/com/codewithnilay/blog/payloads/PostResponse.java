package com.codewithnilay.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

public class PostResponse {
	private int pageNumber;
	private int pageSize;
	private long totalelements;
	private int totalPages;
	private boolean lastPage;
	private List<PostDto> content;

}
