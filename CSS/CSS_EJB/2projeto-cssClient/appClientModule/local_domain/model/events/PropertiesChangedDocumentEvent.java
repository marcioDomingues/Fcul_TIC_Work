package local_domain.model.events;

import local_domain.model.lendables.Lendable;

public class PropertiesChangedDocumentEvent extends RentalCollectionEvent {

	private static final long serialVersionUID = 8844535724909082409L;

	public PropertiesChangedDocumentEvent(Lendable document) {
		super(document);
	}

}
