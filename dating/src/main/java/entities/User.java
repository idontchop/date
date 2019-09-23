/**
 * 
 */
package entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Entity

public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	private Gender gender;
	
	// TODO: Foreign keys
	long profile_id;
	long search_preference_Id;
	long interested_in_id;	
	
	// date user created
	private Date created;
	
	// last user log in
	private Date lastLogin;
	
	public User () {
		gender = Gender.getDefault();
	}
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}

	public long getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(long profile_id) {
		this.profile_id = profile_id;
	}

	public long getSearch_preference_Id() {
		return search_preference_Id;
	}

	public void setSearch_preference_Id(long search_preference_Id) {
		this.search_preference_Id = search_preference_Id;
	}

	public long getInterested_in_id() {
		return interested_in_id;
	}

	public void setInterested_in_id(long interested_in_id) {
		this.interested_in_id = interested_in_id;
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
