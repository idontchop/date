package entities;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Hides
 *
 */
@Entity
@Table(name = "hides")
public class Hides extends Interactions {

	
	private static final long serialVersionUID = 1L;
	
	public Hides () {
		
	}
	
	public Hides ( User from, User to ) {
		super ( from , to );
	}
}
