package com.idontchop.dating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.User;
import entities.UserProfile;
import repositories.UserRepository;
import rest_entities.RestMessage;

/**
 * This class contains the endpoints a user may request for himself.
 * 
 * Such as UserProfile
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@RestController
public class UserEndpoints {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/MyProfile")
	public UserProfile getUserProfile () {
		return getUser().getProfile();
	}
	
	@PostMapping("/MyProfile")
	public RestMessage setUserProfile ( UserProfile userProfile ) {
		getUser().setProfile(userProfile);
		userRepository.save(getUser());
		return new RestMessage("ok");
	}
	
	/**
	 * Helper function, returns the User entity of the currently logged in user
	 */
	private User getUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		return ((CurrentUser) authentication.getPrincipal()).getUser();
	}
}
