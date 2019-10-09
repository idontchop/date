package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.MediaData;

public interface MediaDataRepository extends CrudRepository <MediaData, Long> {

}
