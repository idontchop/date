package datingEntities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Entity implementation class for Entity: Gender
 *
 */
@Entity
public class Gender  {

	

	@Id
	@GeneratedValue
	private long id;
	
	Gender () {
		
	}
   
}
