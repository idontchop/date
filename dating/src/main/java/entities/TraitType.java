package entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;

/**
 * Trait Type is for configuration of profile pages.
 * 
 * For example:
 * 
 * Smokes?
 * 
 * min_values = 1 (almost always 1)
 * max_values = 1
 * This is not 0 because this class is only checked if the user makes a selection.
 * All traits can be 0
 * 
 * 
 * value1 = Never
 * value2 = Sometimes ...
 *
 */
@Entity
@Table ( name = "trait_type" )
public class TraitType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Length ( min = 1, max = 32 )
	private String name;
	
	private int minValues;
	private int maxValues;
	
	@Length ( min = 1, max = 32 )
	private String value1;
	@Length ( min = 1, max = 32 )
	private String value2;
	@Length ( min = 1, max = 32 )
	private String value3;
	@Length ( min = 1, max = 32 )
	private String value4;
	@Length ( min = 1, max = 32 )
	private String value5;
	@Length ( min = 1, max = 32 )
	private String value6;
	@Length ( min = 1, max = 32 )
	private String value7;
	@Length ( min = 1, max = 32 )
	private String value8;
	@Length ( min = 1, max = 32 )
	private String value9;
	@Length ( min = 1, max = 32 )
	private String value10;


	public TraitType() {
		super();
	}   
	public long getId() {
		return this.id;
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
	public int getMinValues() {
		return minValues;
	}
	public void setMinValues(int minValues) {
		this.minValues = minValues;
	}
	public int getMaxValues() {
		return maxValues;
	}
	public void setMaxValues(int maxValues) {
		this.maxValues = maxValues;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	public String getValue7() {
		return value7;
	}
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	public String getValue8() {
		return value8;
	}
	public void setValue8(String value8) {
		this.value8 = value8;
	}
	public String getValue9() {
		return value9;
	}
	public void setValue9(String value9) {
		this.value9 = value9;
	}
	public String getValue10() {
		return value10;
	}
	public void setValue10(String value10) {
		this.value10 = value10;
	}
	
	
   
}
