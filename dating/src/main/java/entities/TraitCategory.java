package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: TraitCategory
 *
 */
@Entity

public class TraitCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;

	@Length ( min = 4, max = 32 )
	private String name;
	
	private Date created;	
	private boolean active;

	public TraitCategory() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
   
}
