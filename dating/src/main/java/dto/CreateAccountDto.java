package dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import entities.User;
import entities.UserProfile;
import entities.UserSecurity;

/**
 * Works with the create account form.
 * 
 * Does not have a matching entity directly, toEntity returns a NEW User.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class CreateAccountDto {

	private final String GENDERCONSTRAINT = "^(Man|Woman)$";
	private final String EMAILCONSTRAINT = "^(.+)@(.+)$";
	private final String BIRTHDAYCONSTRAINT = "(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d";

	@Pattern ( regexp = EMAILCONSTRAINT )
	@NotNull
	private String email;
	
	@Size ( min = 2, max = 14 )
	@NotNull
	private String displayName;
	
	@Pattern ( regexp = GENDERCONSTRAINT )
	@NotNull
	private String gender;
	
	@Pattern ( regexp = BIRTHDAYCONSTRAINT )
	@NotNull
	private String birthday;
	
	@Size ( min = 2, max = 14 )
	@NotNull
	private String username;

	// TODO: 8 min
	@Size ( min = 4, max = 30 )
	@NotNull
	private String password;
	
	public CreateAccountDto () {
		
	}
	
	/**
	 * This Dto will not allow this due to the complexity of setting up a User
	 * 
	 * @return
	 */
	public void toEntity () throws IllegalArgumentException {
		throw new IllegalArgumentException ("Not Allowed - must create conversion outside bean");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getGENDERCONSTRAINT() {
		return GENDERCONSTRAINT;
	}

	public String getEMAILCONSTRAINT() {
		return EMAILCONSTRAINT;
	}

	public String getBIRTHDAYCONSTRAINT() {
		return BIRTHDAYCONSTRAINT;
	}
	
	
}
