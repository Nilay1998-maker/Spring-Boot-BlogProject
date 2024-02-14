package com.codewithnilay.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithnilay.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}
	
	
	public void repoTest() {
		String className=this.userRepo.getClass().getName();
		String packageName=this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}

}
