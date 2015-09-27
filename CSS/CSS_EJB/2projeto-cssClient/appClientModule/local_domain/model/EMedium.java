package local_domain.model;

import java.io.File;


import local_domain.model.events.EMediumListener;

public interface EMedium extends Comparable<EMedium> {
	File getFile ();
	String getTitle ();
	String getAuthor();
	String getMimeType();
	Iterable<String> getTags();
	EMediumType getType();
	void addEMediumListener(EMediumListener listener);
	void removeEMediumListener(EMediumListener listener);
	boolean canBookmarkPage();
	boolean canAnnotate();
	boolean canAnnotatePage();
	EMediumPropertiesData getEMediumProperties();
	boolean isExpired();
}
