package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 
 * Author: Nathaniel J Dunn
 * 
 * Entity implementation class for Entity: UserProfile
 * 
 * One profile per user. Contains info such as 'display name', 'about me', 'looking for', 'instagram url' (?)
 * doesn't have: smoking, drinking, likes, etc, these are "traits"
 * 
 * This is public data. Private data is in UserDetail
 * 
 *
 */
@Entity
@Table( name = "user_profile" )
public class UserProfile implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column ( length = 24 )
	private String displayName;
	
	/**
	 * This is the main profile, it goes on the browse pages.
	 * 
	 */
	@Column ( length = 5000, nullable = false )
	private String aboutMe;
	
	/**
	 * This is a sub profile. Seen only if the user is looking at the profile.
	 */
	@Column ( length = 5000, nullable = false )
	private String lookingFor;
	
	/**
	 * This is the public birthday, input by the user. The user_detail table
	 * might contain a different birthday if verified.
	 */
	@NotNull
	private Date birthday;

	public UserProfile() {
		super();
	}
   
}
