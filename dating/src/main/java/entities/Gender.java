package entities;

import java.io.Serializable;
import java.sql.Date;

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
	
	/* male, female, ladyboy? */
	private String name;
	
	private Date created;
	
	private boolean active;
	
	Gender () {
		
	}

	/**
	 * Factory for classes that need this, since gender is NotNull
	 * @return
	 */
	public static Gender getDefault () {
		Gender g = new Gender();
		
		g.setName("Not Declared");
		g.setActive(true);
		
		return g;
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
   
	
}
