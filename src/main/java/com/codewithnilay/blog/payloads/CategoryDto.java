package com.codewithnilay.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class CategoryDto {
	
	
	
	private Integer categoryId;
	@NotBlank
	@Size(min=4,message="Title must be min 4 char!!")
	private String categoryTitle;
	@NotBlank
	@Size(max=10,message="Description must be max 10 char!!")
	private String categoryDexcription;

}
