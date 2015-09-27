package model.shelves;

import java.io.File;
import java.io.Serializable;

import model.EMediumProperties;
import model.EMediumType;
import model.lendables.Lendable;
import model.rentals.Rental;

public interface EMedium extends Serializable {
	File getFile ();
	String getPath ();
	String getTitle ();
	String getAuthor();
	String getMimeType();
	int getId();
	Iterable<String> getTags();
	EMediumType getType();
	boolean canBookmarkPage();
	boolean canAnnotate();
	boolean canAnnotatePage();
	EMediumProperties getEMediumProperties();
	boolean isExpired();
	Lendable getLendable();
	Rental makeRental();
}
