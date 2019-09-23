package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
