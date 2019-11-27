package com.idontchop.dating;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import entities.Media;
import entities.User;
import repositories.MediaRepository;
import repositories.UserRepository;
import service.MediaService;

/**
 * Handles REST endpoints for images, videos, etc
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@RestController
public class MediaEndpoints {
	
	@Autowired
	private MediaService mService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MediaRepository mRepository;
	
	@PostMapping ("/uploadImage")
	public String uploadImage ( @RequestParam("file") MultipartFile file ) {
		try {
			mService.storeDefaultImage(file, getUser() );
		} catch (IOException e) {
			return "Error: " + e.getMessage();
		}
		return "success";
	}
	
	@GetMapping ("/image/{imageId}")
	public ResponseEntity<Resource> downloadImage (@PathVariable Long imageId) {
		Media m = mRepository.findById(imageId).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("image/jpeg"))
				.header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"profile.jpg\"")
				.body( new ByteArrayResource (m.getData() ) );
						
	}
	
	/**
	 * Helper function, returns the User entity of the currently logged in user
	 */
	private User getUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User u = userRepository.findByUserSecurity_Username(currentPrincipalName);
		return u;
	}

}
