package dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.UserProfile;

/**
 * Data Transfer Object for UserProfile Entity.
 * 
 * use toEntity() to convert to UserProfile.
 * 
 * TODO: extra gender and interestedIn
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class UserProfileDto {

	// TODO: doesn't work, need Configuration class?
	//@Autowired
	private ModelMapper modelMapper;
	
	// Variables in UserProfile
	private long id;
	private String displayName;
	private String aboutMe;
	private String lookingFor;
	private String birthday;
	
	// Extra Variables -- updated in User entity
	private String gender;
	private String interestedIn;
	
	/**
	 * Attempting to encapsulate mapping logic inside the Dto.
	 * A call to this should return the proper entity.
	 * 
	 * @return
	 */
	public UserProfile toEntity () {
		
		UserProfile userProfile = new UserProfile();
		modelMapper = new ModelMapper();
		userProfile = modelMapper.map ( this, UserProfile.class );
		
		return userProfile;
	}
	
	/**
	 * Returns a Json. Should be standard on the DTO objects so we can easily compare with 
	 * JS Frontend
	 */
	@Override
	public String toString () {
		
		String json;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			json = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			json = super.toString() + "-errorJsonToStringUserProfileDto-" + e.getMessage();
		}
		return json;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(String interestIn) {
		this.interestedIn = interestIn;
	}
	
	
	
}
