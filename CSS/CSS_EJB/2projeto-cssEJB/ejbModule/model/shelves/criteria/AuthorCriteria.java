package model.shelves.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class AuthorCriteria extends Criterion {

	@Column(nullable=false)
	private String author;

	AuthorCriteria() {}
	
	public AuthorCriteria (String author) {
		this.author = author;
	}
	
	@Override
	public boolean satisfies(Rental rental) {
		return rental.getAuthor().equals(author) ;
	}

}
