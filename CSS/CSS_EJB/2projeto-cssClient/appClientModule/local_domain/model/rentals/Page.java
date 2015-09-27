package local_domain.model.rentals;

import java.util.LinkedList;


import java.util.List;

import javax.swing.event.EventListenerList;

import local_domain.model.events.EMediaEvent;
import local_domain.model.events.EMediumListener;

public class Page {

	private boolean bookmark;
	private List<String> annotations;
	private int pageNum;
	private EventListenerList listeners;
	
	public Page (int pageNum, EventListenerList listeners) {
		annotations = new LinkedList<String>();
		this.pageNum = pageNum;
		this.listeners = listeners;
	}
	
	public void addAnnotation (String text) {
		annotations.add(text);
		EMediaEvent event = new EMediaEvent (this);
		event.setPageNum(pageNum);
		event.setAnnotationNum(annotations.size() - 1);
		event.setAnnotationText(text);
		event.setHasAnnotations(true);
		fireAnnotationAdded(event);
	}
	
	public Iterable<String> getAnnotations () {
		return annotations;
	}
	
	public String getAnnotationText (int annotNum) {
		return annotations.get(annotNum);
	}

	public boolean isBookmark() {
		return bookmark;
	}

	public void toggleBookmark() {
		bookmark = !bookmark;
		EMediaEvent event = new EMediaEvent (this);
		event.setPageNum(pageNum);
		event.setBookmarked(bookmark);
		fireBookmarkToggled(event);
	}

	public int getPageNum() {
		return pageNum;
	}

	public void removeAnnotation(int annotNum) {
		annotations.remove(annotNum);
		EMediaEvent event = new EMediaEvent (this);
		event.setPageNum(pageNum);
		event.setAnnotationNum(annotNum);
		event.setHasAnnotations(hasAnnotations());
		fireAnnotationRemoved(event);		
	}

	public boolean hasAnnotations() {
		return annotations.size() > 0;
	}

	public boolean isBookmarked() {
		return bookmark;
	}
	
	private void fireBookmarkToggled(EMediaEvent event) {
	   	for (EMediumListener listener : listeners.getListeners(EMediumListener.class))
	   		listener.bookmarkToggled(event);
	}
	
	private void fireAnnotationAdded(EMediaEvent event) {
	  	for (EMediumListener listener : listeners.getListeners(EMediumListener.class))
	   		listener.annotationAdded(event);
	}
	
	private void fireAnnotationRemoved(EMediaEvent event) {
    	for (EMediumListener listener : listeners.getListeners(EMediumListener.class))
    		listener.annotationRemoved(event);
    }
}
