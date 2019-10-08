package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Favorites;
import entities.InteractionsId;


public interface FavoritesRepository extends CrudRepository <Favorites, InteractionsId> { 
   
}
