package repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.UserSecurity;

@RepositoryRestResource ( exported = false )
public interface UserSecurityRepository extends CrudRepository <UserSecurity, Long> {
	

}
