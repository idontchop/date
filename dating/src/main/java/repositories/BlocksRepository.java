package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Blocks;
import entities.InteractionsId;

public interface BlocksRepository extends CrudRepository <Blocks, InteractionsId > {

}
