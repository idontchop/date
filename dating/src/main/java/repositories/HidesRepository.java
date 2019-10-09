package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Hides;
import entities.Interactions;
import entities.InteractionsId;
import entities.User;

public interface HidesRepository extends CrudRepository <Hides, InteractionsId> {

	Iterable<Hides> findAllByFrom(User user);

}
