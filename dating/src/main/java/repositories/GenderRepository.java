package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Gender;

public interface GenderRepository extends CrudRepository <Gender, Long> {

}
