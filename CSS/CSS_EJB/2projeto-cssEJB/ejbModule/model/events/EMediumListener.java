package model.events;

import java.util.EventListener;

public interface EMediumListener extends EventListener {
	void annotationAdded (EMediumEvent event);
	void annotationRemoved (EMediumEvent event);
	void bookmarkToggled (EMediumEvent event);
}
