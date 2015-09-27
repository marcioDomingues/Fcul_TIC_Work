package model.rentals;

import model.EMedium;
import model.EMediumType;
import model.lendables.Lendable;

public class RentalFactory {
	public Rental createRental (EMedium rental) {
		if (rental instanceof Rental)
			return (Rental) rental;
		Lendable lendable = (Lendable) rental;
		if (rental.getType() == EMediumType.DOCUMENT)
			return new BookRental(lendable);
		else
			return new Rental(lendable);
	}
}
