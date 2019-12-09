/**
 * 
 */
package entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;


/**
 * @author Nathaniel J Dunn <idontchop.com>
 * 
 * The goal of the design of this class is to keep it small. Since every query will look at this first.
 * Eager joins are used when the data is thought to be used a lot
 * Lazy joins when not used.
 * 
 * Junctions tables are not even referenced here.
 * The idea is to only query junction tables when necessary. The logic will be done independently.
 *
 */
@Entity
public class User {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@ManyToOne ( fetch = FetchType.EAGER )
	@JoinColumn ( name = "gender_id")
	private Gender gender;
	
	/**
	 * We won't allow multiple interestedIns here and probably should write some constraints.
	 * We aren't a gay site right?
	 */
	@NotNull
	@ManyToOne ( fetch = FetchType.EAGER )
	@JoinColumn ( name = "interested_in_id")
	private Gender interestedIn;
	
	/**
	 * User Profile contains public information. 
	 * 
	 * Fetched eager mainly because of birthday. Will always need that.
	 */
	@NotNull
	@OneToOne ( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinColumn ( name = "profile_id" )
	private UserProfile profile;
	
	/**
	 * User Security, this class never on endpoints
	 */
	@NotNull
	@OneToOne ( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn ( name = "security_id" )
	private UserSecurity userSecurity;
	
	@OneToMany ( mappedBy="user", fetch = FetchType.EAGER )
	@OrderBy ( value = "priority" )
	private List<Media> media;
	
	
	// TODO: Foreign keys
	//long search_preference_Id;
	
	// date user created
	private Date created;
	
	// last user log in
	private Date lastLogin;
	
	
	public User () {
		created = new Date();
		lastLogin = new Date();
		gender = Gender.getDefault();
		interestedIn = Gender.getDefault();
	}
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
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
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public Gender getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(Gender interestedIn) {
		this.interestedIn = interestedIn;
	}

	public UserSecurity getUserSecurity() {
		return userSecurity;
	}

	public void setUserSecurity(UserSecurity userSecurity) {
		this.userSecurity = userSecurity;
	}

	public List<Media> getMedia() {
		return media;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/*
	 * Juction tables decoupled from class:
	 * favorites
	 * likes
	 * ...
	 */
	


}
