package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.UserProfile;

public interface UserProfileRepository extends CrudRepository <UserProfile, Long >{

}
