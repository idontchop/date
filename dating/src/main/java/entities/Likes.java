package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 * 
 * ID class for Favorites Junction. see entities.Likes.java
 *
 */
@Entity
@Table ( name = "likes" )
public class Likes extends Interactions {

	
	private static final long serialVersionUID = 1L;

	public Likes () {
		
	}
	
	public Likes ( User from, User to ) {
		super ( from, to );
	}
	
}
