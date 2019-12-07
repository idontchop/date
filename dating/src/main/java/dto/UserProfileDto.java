package dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Gender;
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

	@JsonIgnore
	@Transient
	private final DateTimeFormatter DTFORMAT = 
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
	// TODO: doesn't work, need Configuration class?
	//@Autowired
	private ModelMapper modelMapper;
	
	// Variables in UserProfile
	
	// should display main id, not userprofile key
	private long id;
	private String displayName;
	private String aboutMe;
	private String lookingFor;
	
	// Birthday should never be transmitted
	// age is set to the month not day
	private int age;
	
	
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
	 * Sets the DTO data from the supplied userprofile
	 * 
	 * NOTE: Leaves gender, interestedIn as null, call
	 * fromEntity ( UserProfile, Gender, Gender)
	 * @param userProfile
	 */
	public void fromEntity ( UserProfile userProfile ) {
		id = userProfile.getId();
		displayName = userProfile.getDisplayName();
		aboutMe = userProfile.getAboutMe();
		lookingFor = userProfile.getLookingFor();
		setAge(userProfile.getBirthday().toString());
		
	}
	
	public void fromEntity ( UserProfile userProfile, Gender gender, Gender interestedIn ) {
		this.gender = gender.getName();
		this.interestedIn = interestedIn.getName();
		fromEntity(userProfile);
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

	/**
	 * returns age
	 * @return
	 */
	public String getBirthday() {
		return String.valueOf(age);
	}

	/**
	 * Will set age
	 * @param birthday
	 */
	public void setBirthday(String birthday) {
		setAge(birthday);
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
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setAge(String birthday) {
		
		YearMonth ym = YearMonth.parse(birthday, DTFORMAT);
		LocalDate dt = ym.atDay(1);
		age = Period.between(dt, LocalDate.now()).getYears();
		
	}
	
}
