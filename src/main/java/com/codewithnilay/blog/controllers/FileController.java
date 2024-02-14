package com.codewithnilay.blog.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import org.springframework.util.StreamUtils;


import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithnilay.blog.payloads.FileResponse;
import com.codewithnilay.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {
		
		String fileName=null;
		try {
			fileName = this.fileService.uploadImage(path, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "Image is not uploaded due to server error !!"), HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<>(new FileResponse(fileName, "Image is successfully uploaded!!"), HttpStatus.OK);

	}
	
	// method to serve file
	@GetMapping(value="/profiles/{imageName}",produces="image/jpeg")
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
