package model.rentals;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.swing.event.EventListenerList;

import model.events.EMediaEvent;
import model.events.EMediumListener;
import model.transactions.DBHelper;

@Entity
public class Page  {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int ID;
	
	/* class attributes */
	//this page number
	@Column(name="NUMBER")
	private int pageNum;
	// flag over if this page is bookmarked
	@Column(name="IS_BOOKMARKED")
	private boolean isBookmarked;
	
	//the list of annotations of this page
	@ElementCollection
	private List<String> annotations;

	@Transient
	private EventListenerList listeners;
	
	//for JPA
	public Page(){}

	public Page(int pageNum, EventListenerList listeners) {
		annotations = new LinkedList<String>();
		this.pageNum = pageNum;
		this.listeners = listeners;
	}
	
	public void setListeners( EventListenerList listeners ) {
		this.listeners = listeners;
	}

	public void addAnnotation(String text) {
		annotations.add(text);
		EMediaEvent event = new EMediaEvent(this);
		event.setPageNum(pageNum);
		event.setAnnotationNum(annotations.size() - 1);
		event.setAnnotationText(text);
		event.setHasAnnotations(true);
		fireAnnotationAdded(event);
	}

	public Iterable<String> getAnnotations() {
		return annotations;
	}

	public String getAnnotationText(int annotNum) {
		return annotations.get(annotNum);
	}

	public boolean isBookmark() {
		return isBookmarked;
	}

	public void toggleBookmark() {
		isBookmarked = !isBookmarked;
		EMediaEvent event = new EMediaEvent(this);
		event.setPageNum(pageNum);
		event.setBookmarked(isBookmarked);
		fireBookmarkToggled(event);
	}

	public int getPageNum() {
		return pageNum;
	}

	public void removeAnnotation(int annotNum) {
		annotations.remove(annotNum);
		EMediaEvent event = new EMediaEvent(this);
		event.setPageNum(pageNum);
		event.setAnnotationNum(annotNum);
		event.setHasAnnotations(hasAnnotations());
		fireAnnotationRemoved(event);
	}

	public boolean hasAnnotations() {
		return annotations.size() > 0;
	}

	public boolean isBookmarked() {
		return isBookmarked;
	}

	private void fireBookmarkToggled(EMediaEvent event) {
		for (EMediumListener listener : listeners
				.getListeners(EMediumListener.class))
			listener.bookmarkToggled(event);
	}

	private void fireAnnotationAdded(EMediaEvent event) {
		for (EMediumListener listener : listeners
				.getListeners(EMediumListener.class))
			listener.annotationAdded(event);
	}

	private void fireAnnotationRemoved(EMediaEvent event) {
		for (EMediumListener listener : listeners
				.getListeners(EMediumListener.class))
			listener.annotationRemoved(event);
	}
}
