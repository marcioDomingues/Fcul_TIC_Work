package model.shelves.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import model.rentals.Rental;

@Entity

@NamedQueries({
	 @NamedQuery(name = Criteria.FIND_ALL, query = "SELECT c FROM Criteria c"),
	 @NamedQuery(name = Criteria.FIND_ALL, query = "SELECT c FROM Criteria c"),
	 @NamedQuery(name=Criteria.FIND_BY_TAG, query="SELECT c FROM Criteria c WHERE c.tag = :"+Criteria.FIND_TAG)
})

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class Criteria implements Criterion, Comparable<Criteria> {

	// Named query name constants
	public static final String FIND_ALL = "Criteria.findAll";
	public static final String FIND_BY_TAG = "Criteria.findByTag";
	public static final String FIND_TAG = "tag";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column (name="Type",nullable=false)
	private String tag;

	//Constructor
	public Criteria(){};
	
	public Criteria(String tag){
		this.setCriteriaName(tag);
	}

	//Setters
	
	public void setCriteriaName(String tag) {
		this.tag = tag;
	}
	
	//Getters
	public String getTag() {
		return tag;
	}

	//Comparable
	@Override
	public int compareTo(Criteria other) {
		return tag.compareTo(other.tag);
	}	
	
	public abstract boolean satisfies (Rental rental);
}
