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
@IdClass ( BlocksId.class )
public class Blocks implements Serializable {

	   
	@Id
	@Column ( name = "from_id", insertable = false, updatable = false )
	private long fromId;
	
	@Id
	@Column ( name = "from_id", insertable = false, updatable = false )
	private long toId;
	
	@ManyToOne
	@JoinColumn ( name = "from_id", insertable = false, updatable = false )
	private User from;
	
	@ManyToOne
	@JoinColumn ( name = "from_id", insertable = false, updatable = false )
	private User to;
	
	private boolean active = true;
	
	private Date created;
	
	
	private static final long serialVersionUID = 1L;

	public Blocks() {
		super();
	}
	
	/* Getters and Setters */

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


	
}
