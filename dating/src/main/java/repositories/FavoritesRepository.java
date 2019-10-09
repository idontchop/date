package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Favorites;
import entities.Interactions;
import entities.InteractionsId;
import entities.User;


public interface FavoritesRepository extends CrudRepository <Favorites, InteractionsId> {

	Iterable<Favorites> findAllByFrom(User user); 
   
}
