package model;

import java.io.File;

import model.events.EMediumListener;

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
	EMedium getItem();
	
	int getID();
}
