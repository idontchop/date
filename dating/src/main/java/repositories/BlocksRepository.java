package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Blocks;
import entities.Interactions;
import entities.InteractionsId;
import entities.User;

public interface BlocksRepository extends CrudRepository <Blocks, InteractionsId > {

	Iterable<Blocks> findAllByFrom(User user);

}
