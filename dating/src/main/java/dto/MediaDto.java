package dto;

/**
 * Data Transfer Object for Media.
 * 
 * Used at MyImages endpoint.
 * 
 * These values may be set freely by the user:
 * 
 * active, priority
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class MediaDto {


	private long id;
	private int type;
	private boolean active;
	private int validated;
	private int priority;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getValidated() {
		return validated;
	}
	public void setValidated(int validated) {
		this.validated = validated;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	
}
