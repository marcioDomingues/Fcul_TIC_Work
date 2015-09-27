package model.shelves.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class AuthorCriteria extends Criteria {
	
	@Column (name="Author", nullable=false)
	private String author;

	public AuthorCriteria(){};

	public AuthorCriteria (String author) {
		//id from abstract class
		super("AuthorCriteria");
		this.setAuthorCriteria(author);
	}
	
	public String getAuthorCriteria(){
		return author;
	}
	
	public void setAuthorCriteria(String author){
		this.author = author;
	}
	
	@Override
	public boolean satisfies(Rental rental) {
		return rental.getAuthor().equals(author) ;
	}
	
}
