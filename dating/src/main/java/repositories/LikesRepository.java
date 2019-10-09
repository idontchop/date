package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Interactions;
import entities.InteractionsId;
import entities.Likes;
import entities.User;

public interface LikesRepository extends CrudRepository <Likes, InteractionsId> {

	Iterable<Likes> findAllByFrom(User user);

}
