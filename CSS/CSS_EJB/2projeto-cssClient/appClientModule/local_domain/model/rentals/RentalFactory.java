package local_domain.model.rentals;

import local_domain.model.EMedium;
import local_domain.model.EMediumType;
import local_domain.model.lendables.Lendable;

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
