package entities;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Favorites
 *
 * See Interactions superclass
 */
@Entity
@Table(name = "favorites")
public class Favorites extends Interactions {

	
	private static final long serialVersionUID = 1L;
	
	public Favorites ( User from, User to ) {
		super ( from, to );
	}

	public Favorites() {
		super();
	}
   
}