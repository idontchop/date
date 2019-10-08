package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


/**
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 * 
 * ID class for Interactions Junction. see entities.Favorites.java
 *
 */

public class InteractionsId implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Column ( name = "from_id" )
	private long fromId;
	
	@Column ( name = "to_id" )
	private long toId;

	public InteractionsId () {
		
	}
	
	public InteractionsId ( long fromId, long toId ) {
		this.fromId = fromId;
		this.toId = toId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fromId ^ (fromId >>> 32));
		result = prime * result + (int) (toId ^ (toId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InteractionsId other = (InteractionsId) obj;
		if (fromId != other.fromId)
			return false;
		if (toId != other.toId)
			return false;
		return true;
	}
	
	
}

