package model.events;

import java.util.EventListener;

public interface EMediaCollectionListener extends EventListener {

	void RentalAdded (RentalCollectionEvent event);
	
	void RentalRemoved (RentalCollectionEvent event);
}
