package service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import entities.User;
import repositories.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
		
		User currentUser = userRepository.findByUserSecurity_Username ( username );
		
		UserDetails user = new org.springframework.security.core.userdetails
				.User ( currentUser.getUserSecurity().getUsername(),
						currentUser.getUserSecurity().getPassword(),
				AuthorityUtils.createAuthorityList("USER"));
		return user;
		
	}

}
