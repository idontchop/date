package entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: userSecurity
 * 
 * Contains username / password.
 * Separated so every user query doesn't need this.
 */
@Entity
@Table ( name = "user_security" )
public class UserSecurity implements Serializable {

	   
	@Id
	@GeneratedValue
	private long id;
	
	@Length ( min = 4, max = 16)
	private String username;
	
	@Length ( min = 4, max = 32 )
	private String password;
	
	private static final long serialVersionUID = 1L;

	public UserSecurity() {
		super();
	}   
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
   
}
