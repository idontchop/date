package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: MediaData
 * 
 * This does nothing but store a blob for the image data. This is to help
 * insure that images are not loaded when only type info needed from the image.
 */
@Entity

public class MediaData implements Serializable {

	@Id
	@GeneratedValue
	private long id;
	
	@Lob
	private byte[] data;
	
	private static final long serialVersionUID = 1L;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public MediaData() {
		super();
	}
   
}
