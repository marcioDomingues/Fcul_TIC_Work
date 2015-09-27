package model.shelves.criteria;

import model.rentals.Rental;

public interface Criterion {
	boolean satisfies (Rental rental);
}
