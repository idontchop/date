/**
 * 
 */
package datingEntities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Entity

public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	// TODO: Foreign keys
	long profile_id;
	long search_preference_Id;
	long interested_in_id;
	long gender_id;
	
	// date user created
	private Date created;
	
	// last user log in
	private Date lastLogin;
	
	

}
