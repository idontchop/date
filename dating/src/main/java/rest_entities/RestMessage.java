package rest_entities;

/**
 * For returning simple messages to the client.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class RestMessage {
	
	private String message = "";
	
	private String user = "";
	
	private String target = "";
	
	public RestMessage ( String message ) {
		this.message = message;
	}
	
	public RestMessage ( String message, String user, String target ) {
		this.message = message; this.user = user; this.target = target;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	
}
