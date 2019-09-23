package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Favorites
 *
 */
@Entity
@Table(name = "favorites")
@IdClass(FavoritesId.class)
public class Favorites implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "from_id", insertable = false, updatable = false )
	private long fromId;

	@Id
	@Column( name = "to_id", insertable = false, updatable = false )
	private long toId;
	
	@ManyToOne
	@JoinColumn(name="from_id",insertable = false, updatable = false )
	private User from;
	
	@ManyToOne
	@JoinColumn(name="to_id",insertable = false, updatable = false )
	private User to;
		
	private boolean active = true;
	
	private Date created;
	
	public User getFrom() {
		return from;
	}



	public void setFrom(User from) {
		this.from = from;
	}



	public User getTo() {
		return to;
	}



	public void setTo(User to) {
		this.to = to;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
	}


	public long getFromId() {
		return fromId;
	}



	public void setFromId(long fromId) {
		this.fromId = fromId;
	}



	public long getToId() {
		return toId;
	}



	public void setToId(long toId) {
		this.toId = toId;
	}

	


	public Favorites() {
		super();
	}
   
}
