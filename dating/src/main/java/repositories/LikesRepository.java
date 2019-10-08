package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.InteractionsId;
import entities.Likes;

public interface LikesRepository extends CrudRepository <Likes, InteractionsId> {

}
