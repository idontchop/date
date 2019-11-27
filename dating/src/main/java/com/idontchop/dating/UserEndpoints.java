package com.idontchop.dating;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dto.UserProfileDto;
import entities.Gender;
import entities.User;
import entities.UserProfile;
import repositories.UserRepository;
import repositories.GenderRepository;
import repositories.UserProfileRepository;
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
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private GenderRepository genderRepository;
	
	@GetMapping("/MyProfile")
	public UserProfile getUserProfile () {
		return getUser().getProfile();
	}
	
	@PostMapping(path = "/MyProfile", headers = "Accept=application/json" )
	public RestMessage setUserProfile ( @Valid @RequestBody UserProfileDto userProfileDto ) {
		
		// Set Profile with Entity Map
		getUser().setProfile( userProfileDto.toEntity() );
		
		// Extract Gender from DTO
		Gender newGender = genderRepository.findByName(userProfileDto.getGender());
		if ( newGender == null ) return new RestMessage("Error in Gender");
		getUser().setGender(newGender);
		
		// Extract Gender for interested In
		newGender = genderRepository.findByName(userProfileDto.getInterestedIn());
		if ( newGender == null ) return new RestMessage("Error in InterestedIn");
		getUser().setInterestedIn(newGender);
		
		// save User
		userRepository.save(getUser());	// save user
		
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
