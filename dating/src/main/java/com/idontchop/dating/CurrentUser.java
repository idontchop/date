package com.idontchop.dating;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import entities.User;

/**
 * This extension of spring security User allows us to save additional information
 * into the Spring Security Principal. The goal is to limit database accesses when
 * we need the ID, username, and other information from about the current user.
 * 
 * See UserDetailServiceImpl.java
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 1L;

	/**
	 * Saving the User entity here for easy access. 
	 */
	private User user;

	/**
	 * Unused as if 12/1/2019
	 */
	public CurrentUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

	}

	/**
	 * Used by UserDetailServiceImpl
	 */
	public CurrentUser(String username, String password, List<GrantedAuthority> createAuthorityList) {
		super (username, password, createAuthorityList);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
