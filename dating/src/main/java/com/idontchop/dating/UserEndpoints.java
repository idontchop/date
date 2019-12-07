package com.idontchop.dating;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dto.CreateAccountDto;
import dto.UserProfileDto;
import entities.Gender;
import entities.InteractionsId;
import entities.User;
import entities.UserProfile;
import entities.UserSecurity;
import repositories.UserRepository;
import repositories.UserSecurityRepository;
import repositories.BlocksRepository;
import repositories.GenderRepository;
import repositories.HidesRepository;
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
	private BlocksRepository blocksRepository;
	
	@Autowired
	private HidesRepository hidesRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserSecurityRepository userSecurityRepository;
	
	@Autowired
	private GenderRepository genderRepository;
	
	/**
	 * Used to view another user's profile. Checks for hides/etc
	 * 
	 * throws error if id not found or cannot be viewed
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/ViewProfile")
	public ResponseEntity<UserProfileDto> viewUserProfile ( @RequestParam ("id") long id ) {
		
		HttpHeaders responseHeaders = new HttpHeaders(); // For use in setting  message
		
		User viewUser;
		try {
			viewUser = userRepository.findById(id).get();
		} catch ( NoSuchElementException e ) {
			// If user not found return bad request
			responseHeaders.set("Date-Message","User not Found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.headers(responseHeaders)
					.body(new UserProfileDto());
		}
		
		// Check for blocks 
		// Hides not checked for here since hides only removes the user from searches
		
		// set vice versa Interactions composite keys
		InteractionsId interactionKeyFrom = new InteractionsId( getUser().getId(), viewUser.getId() );
		InteractionsId interactionKeyTo = new InteractionsId( viewUser.getId(), getUser().getId() );
		
		// Check if blocked by user
		if ( blocksRepository.findById(interactionKeyFrom).isPresent() ) {
			responseHeaders.set("Date-Message", "Blocked By User");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.headers(responseHeaders)
					.body(new UserProfileDto());
		}
		
		// Check if blocked by target
		if ( blocksRepository.findById(interactionKeyTo).isPresent() ) {
			responseHeaders.set("Date-Message", "Blocked By Target");
			// Does this give away too much info? 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.headers(responseHeaders)
					.body(new UserProfileDto());
		}
		
		
		UserProfileDto viewUserProfileDto = new UserProfileDto();
		viewUserProfileDto.fromEntity(viewUser.getProfile(), viewUser.getGender(), viewUser.getInterestedIn() );
		viewUserProfileDto.setId(viewUser.getId());
		
		responseHeaders.set("Date-Message","Found User Profile");
		return ResponseEntity.ok()
			.headers(responseHeaders)
			.body(viewUserProfileDto);
		//return new ResponseEntity<>(viewUserProfileDto, HttpStatus.OK);
	}
	
	@GetMapping("/MyProfile")
	public UserProfileDto getUserProfile () {
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.fromEntity(getUser().getProfile(), getUser().getGender(), getUser().getInterestedIn());
		return userProfileDto;
	}
	
	@PostMapping(path = "/MyProfile", headers = "Accept=application/json" )
	public RestMessage setUserProfile ( @Valid @RequestBody UserProfileDto userProfileDto ) {
		
		// Set Profile with Entity Map
		getUser().setProfile( userProfileDto.toEntity() );
		
		// Extract Gender from DTO
		Gender newGender = genderRepository.findByName(userProfileDto.getGender());
		if ( newGender == null ) return new RestMessage("Error in Gender");
		getUser().setGender(newGender);
		
		// Extract Gender for interested In from DTO
		Gender newInterestedIn = genderRepository.findByName(userProfileDto.getInterestedIn());
		if ( newInterestedIn == null ) return new RestMessage("Error in InterestedIn");
		getUser().setInterestedIn(newInterestedIn);
		
		// save User
		userRepository.save(getUser());	// save user
		
		return new RestMessage("ok");
	}
	
	@GetMapping ("/createAccountForm")
	public CreateAccountDto getCreateAccountForm () {
		return new CreateAccountDto();
	}
	
	/**
	 * This endpoint is response for accepting a CreateAccountDto and converting the 
	 * data into a new account which is saved to the database.
	 * @param createAccountDto
	 * @return
	 */
	@PostMapping ( path = "/createAccountForm", headers = "Accept=application/json" )
	public ResponseEntity<String> createAccount 
		( @Valid @RequestBody CreateAccountDto createAccountDto ) throws Exception {
		
		// Define Gender
		Gender gender = genderRepository.findByName(createAccountDto.getGender());
		Gender interestedIn = createAccountDto.getGender().equals("Men") ?
				genderRepository.findByName("Woman") : genderRepository.findByName("Man");
		
		// Define User Profile
		UserProfile userProfile = new UserProfile();
		userProfile.setNewUser();
		userProfile.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(createAccountDto.getBirthday()));
		userProfile.setDisplayName(createAccountDto.getDisplayName());
		
		// Define simple username + password security
		UserSecurity userSecurity = new UserSecurity();
		userSecurity.setUsername(createAccountDto.getUsername());
		userSecurity.setPassword(createAccountDto.getPassword());
				
		// Create User and save
		User newUser = new User();
		newUser.setGender(gender);
		newUser.setInterestedIn(interestedIn);
		newUser.setProfile(userProfile);
		newUser.setUserSecurity(userSecurity);
		
		userRepository.save(newUser);
		
		return new ResponseEntity<> ( "Success - Welcome!", HttpStatus.CREATED);
	}
	
	/**
	 * Helper function, returns the User entity of the currently logged in user
	 */
	private User getUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		return ((CurrentUser) authentication.getPrincipal()).getUser();
	}
}
