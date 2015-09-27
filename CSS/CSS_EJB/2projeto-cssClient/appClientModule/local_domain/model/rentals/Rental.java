package local_domain.model.rentals;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;

import local_domain.model.EMedium;
import local_domain.model.EMediumPropertiesData;
import local_domain.model.EMediumType;
import local_domain.model.events.EMediumListener;
import local_domain.model.lendables.Lendable;
import local_domain.adts.Pair;

public class Rental implements EMedium {

	private Lendable lendable;
	private Date timestamp;
	private boolean expired;
	private List<String> annotations;
	protected EventListenerList listeners;
	
	public Rental(Lendable lendable) {
		this.lendable = lendable;
		timestamp = new Date();
		annotations = new LinkedList<String>();
		listeners = new EventListenerList();
	}
	
	public String getAuthor() {
		return lendable.getAuthor();
	}
		
	public Date getRentalTimestamp() {
		return timestamp;
	}

	public void addAnnotation(String text) {
		annotations.add(text);
	}
	
	// @requires a valid annotNum
	public void removeAnnotation(int annotNum) {
		annotations.remove(annotNum);
	}

	public Iterable<String> getAnnotations(int pageNum) {
		return annotations;
	}
	
	public List<Pair<Integer,List<String>>> getAnnotatins() {
		List<Pair<Integer,List<String>>> allAnnotations = new LinkedList<Pair<Integer,List<String>>>();
		allAnnotations.add(new Pair<Integer, List<String>>(1, annotations));
		return allAnnotations;
	}

	public String getAnnotationText(int annotNum) {
		return annotations.get(annotNum);
	}

	public boolean hasAnnotations(int pageNum) {
		return !annotations.isEmpty();
	}

	public File getFile() {
		return lendable.getFile();
	}

	public String getMimeType() {
		return lendable.getMimeType();
	}

	@Override
	public String getTitle() {
		return lendable.getTitle();
	}

	@Override
	public List<String> getTags() {
		return lendable.getTags();
	}

	@Override
	public void addEMediumListener(EMediumListener listener) {
        listeners.add(EMediumListener.class, listener);
	}

	@Override
	public void removeEMediumListener(EMediumListener listener) {
        listeners.remove(EMediumListener.class, listener);
	}

	@Override
	public boolean canBookmarkPage() {
		return false;
	}

	@Override
	public boolean canAnnotate() {
		return true;
	}

	@Override
	public boolean canAnnotatePage() {
		return false;
	}

	@Override
	public EMediumType getType() {
		return lendable.getType();
	}

	@Override
	public int compareTo(EMedium o) {
		return lendable.compareTo(o);
	}

	@Override
	public EMediumPropertiesData getEMediumProperties() {
		return lendable.getEMediumProperties();
	}

	public void renew() {
		expired = false;
	}
	
	@Override
	public boolean isExpired() {
		return expired;
	}

}
