package model.events;

import java.util.EventListener;

public interface ShelfCollectionListener extends EventListener {

	void shelfAdded (ShelfCollectionEvent event);
	
	void shelfRemoved (ShelfCollectionEvent event);
}
