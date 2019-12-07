package com.idontchop.dating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dto.MediaDto;
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
	public String uploadImage ( @RequestParam("file") MultipartFile file,
								@RequestParam( value = "priority",
											defaultValue = "0",
											required = false) int priority) {
		try {
			mService.storeProfileImage(file, priority, getUser() );
		} catch (IOException e) {
			return "Error: " + e.getMessage();
		}
		return "success";
	}
	
	@DeleteMapping ( "/deleteImage" )
	public ResponseEntity<String> deleteImage ( @RequestParam ("id") long id ) {
		if (mService.deleteProfileImage(id, getUser())) {
			// deleted success
			return new ResponseEntity<>("Success",HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Not Found", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping ("/MyImages")
	public Iterable<Media> getMyImages () {
		return mRepository.findByUser(getUser());
	}
	
	/**
	 * This accepts a new list from the client, to update values.
	 * 
	 * This won't check for a missing media for now TODO
	 * @param newMedia
	 * @return
	 */
	@PostMapping ( path = "/MyImages", headers = "Accept=application/json" )
	public ResponseEntity<String> postMyImages ( @RequestBody ArrayList<MediaDto> newMediaDto ) {
		
	
		// check that each media matches the user and then update
		// only updatable attributes: priority, active
		ArrayList<Media> updatedMedia = new ArrayList<>();
		for ( MediaDto newMedia : newMediaDto)  {
			Optional<Media> media = mRepository.findById(newMedia.getId());
			if ( media.isPresent() ) {
				
				if ( media.get().getUser().getId() == getUser().getId() ) {
					// correct user,  should be good, change the values and save to arraylist
					media.get().setPriority(newMedia.getPriority());
					media.get().setActive(newMedia.isActive());
					updatedMedia.add(media.get());
				} else {
					return new ResponseEntity<>("Incorrect User", HttpStatus.BAD_REQUEST);
				}
				
			} else {
				return new ResponseEntity<>("Media not found: " + newMedia.getId(), HttpStatus.BAD_REQUEST);
			}
		};
		
		updatedMedia.forEach( (media) -> {
			mRepository.save(media);
		});
		return new ResponseEntity<>("Success", HttpStatus.OK);
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
