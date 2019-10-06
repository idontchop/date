package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUserSecurity_Username(String username);
	Page<User> findByGender_ActiveTrue( Pageable p );
	
}
