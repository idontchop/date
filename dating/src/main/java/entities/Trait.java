package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Trait is such things as smoking / likes outdoors / etc
 * Trait category would be the heading, which is mainly used to determine if it's on the page
 * Trait type determines if the selection is a checkbox and what values are allowed.
 * 	Trait type is what the frontend uses to give options. Trait is the storage after constraints checked.
 *
 */
@Entity

public class Trait implements Serializable {

	   
	@Id
	@GeneratedValue
	private long id;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne ( fetch = FetchType.EAGER )
	@JoinColumn ( name = "category_id" )
	private TraitCategory traitCategory;
	
	@ManyToOne ( fetch = FetchType.EAGER )
	@JoinColumn ( name = "trait_type" )
	private TraitType traitType;

	public Trait() {
		super();
	}   
	
	
	public TraitCategory getTraitCategory() {
		return traitCategory;
	}


	public void setTraitCategory(TraitCategory traitCategory) {
		this.traitCategory = traitCategory;
	}


	public TraitType getTraitType() {
		return traitType;
	}


	public void setTraitType(TraitType traitType) {
		this.traitType = traitType;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
   
}
