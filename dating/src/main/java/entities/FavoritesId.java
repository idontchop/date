package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 * 
 * ID class for Favorites Junction. see entities.Favorites.java
 *
 */

public class FavoritesId implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Column( name = "from_id" )
	protected long fromId;
	
	@Column( name = "to_id" )
	protected long toId;

	public FavoritesId() {

	}
	
	public FavoritesId( long from, long to ) {
		this.fromId = from;
		this.toId = to;
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
		FavoritesId other = (FavoritesId) obj;
		if (fromId != other.fromId)
			return false;
		if (toId != other.toId)
			return false;
		return true;
	}




}
