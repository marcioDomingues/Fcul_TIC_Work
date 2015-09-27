package model.events;

import java.util.EventListener;

public interface EMediumListener extends EventListener {

	void annotationAdded (EMediaEvent event);
	void annotationRemoved (EMediaEvent event);

	void bookmarkToggled (EMediaEvent event);
}
