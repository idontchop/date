package dto;

import java.util.Date;
import java.util.List;

import entities.Gender;
import entities.Media;
import entities.User;
import entities.UserProfile;

public class UserDto {

	private long id;

	private Gender gender;
	private Gender interestedIn;
	
	private UserProfileDto profile;
	
	private List<Media> media;
	
	private Date created;
	private Date lastLogin;
	
	/**
	 * TODO: lots of sanity checking
	 * 
	 * Sets this Dto's data based on the passed in User
	 * 
	 * @param user
	 */
	public void fromEntity( User user ) {
		this.id = user.getId();
		this.gender = user.getGender();
		this.interestedIn = user.getInterestedIn();
		this.profile = new UserProfileDto();
		this.profile.fromEntity(user.getProfile());
		this.media = user.getMedia();
		this.created = user.getCreated();
		this.lastLogin = user.getLastLogin();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Gender getInterestedIn() {
		return interestedIn;
	}
	public void setInterestedIn(Gender interestedIn) {
		this.interestedIn = interestedIn;
	}
	public UserProfileDto getProfile() {
		return profile;
	}
	public void setProfile(UserProfileDto profile) {
		this.profile = profile;
	}
	public List<Media> getMedia() {
		return media;
	}
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	
}
