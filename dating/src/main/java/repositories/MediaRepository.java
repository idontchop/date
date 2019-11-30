package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Media;
import entities.User;

public interface MediaRepository extends CrudRepository <Media, Long > {

	Iterable<Media> findByUser(User user);

}
