package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;

/**
 * Entity implementation class for Entity: Blocks
 *
 */
@Entity
@Table ( name = "blocks" )
public class Blocks extends Interactions {

	private static final long serialVersionUID = 1L;

	public Blocks( User from, User to ) {
		super ( from, to );
	}
	
	public Blocks() {
		super();
	}

}
