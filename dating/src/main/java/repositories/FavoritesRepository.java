package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Favorites;
import entities.FavoritesId;

public interface FavoritesRepository extends CrudRepository <Favorites, FavoritesId> { 
   
}
