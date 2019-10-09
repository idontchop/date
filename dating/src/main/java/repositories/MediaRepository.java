package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Media;

public interface MediaRepository extends CrudRepository <Media, Long > {

}
