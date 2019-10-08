package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Hides;
import entities.InteractionsId;

public interface HidesRepository extends CrudRepository <Hides, InteractionsId> {

}
