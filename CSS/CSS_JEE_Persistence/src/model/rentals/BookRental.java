package model.rentals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.swing.event.EventListenerList;

import model.lendables.Lendable;
import model.transactions.DBHelper;
import adts.Pair;


@Entity

/*
 * @param 
 */
public class BookRental extends Rental {
	
	@Column (name="lastPageVisited")
	private int lastPageVisited;

	@OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.MERGE} )
	@JoinColumn(name="BOOK_ID")
	private Map<Integer, Page> pages;

	public BookRental(){};
	
	public BookRental(Lendable book) {
		super(book);
		pages = new TreeMap<Integer, Page> (); 
	}
	
	//need this for the tests
	public BookRental( Lendable book ,Map<Integer, Page> p, Integer i ) {
		super(book);
		pages = new TreeMap<Integer, Page> (); 
		pages = p; 
		lastPageVisited = i;
	}
	
	
	@PostLoad
	public void initialize() {
		listeners = new EventListenerList();
		for ( Page page : pages.values()) {
			page.setListeners(listeners);
		}
	}
	
	public void addAnnotation(int pageNum, String text) {
		getCreatePage(pageNum).addAnnotation(text);
		DBHelper.INSTANCE.update(this);
	}
	
	public void removeAnnotation(int pageNum, int annotNum) {
		Page page = pages.get(pageNum);
		if (page != null) {
			page.removeAnnotation (annotNum);
			DBHelper.INSTANCE.update(this);
		}
	}

	public Iterable<String> getAnnotations(int pageNum) {
		Page p = pages.get(pageNum);
		if (p != null) 
			return p.getAnnotations();
		return new LinkedList<String>();
	}

	public String getAnnotationText(int pageNum, int annotNum) {
		Page p = pages.get(pageNum);
		return p != null ? p.getAnnotationText(annotNum) : "";
	}

	public boolean hasAnnotations(int pageNum) {
		Page p = pages.get(pageNum);
		return p != null && p.hasAnnotations ();
	}

	public boolean isBookmarked(int pageNum) {
		Page p = pages.get(pageNum);
		return p != null && p.isBookmarked();
	}

	public List<Integer> getBookmarks () {
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (Page p : pages.values())
			if (p.isBookmark())
				l.add(p.getPageNum());
		return l;
	}

	public void toggleBookmark(int pageNum) {
		getCreatePage(pageNum).toggleBookmark();
		DBHelper.INSTANCE.update(this);
	}

	public int getLastPageVisited() {
		return lastPageVisited;
	}

	public void setLastPageVisited(int lastPageVisited) {
		this.lastPageVisited = lastPageVisited;
		DBHelper.INSTANCE.update(this);
	}
	
	@Override
	public boolean canBookmarkPage() {
		return true;
	}
	
	@Override
	public boolean canAnnotatePage() {
		return true;
	}

	private Page getCreatePage(int pageNum) {
		Page page = pages.get(pageNum);
		if (page == null) {
			page = new Page(pageNum, listeners);
			pages.put(pageNum, page);
			DBHelper.INSTANCE.store(page);
			DBHelper.INSTANCE.update(this);
		}
		return page;
	}

	public List<Pair<Integer,List<String>>> getAnnotatins() {
		List<Pair<Integer,List<String>>> allAnnotations = new LinkedList<Pair<Integer, List<String>>>();
		for (Entry<Integer, Page> entry : pages.entrySet()) {
			List<String> pageAnnotations = new LinkedList<String>();
			for (String annotation : entry.getValue().getAnnotations())
				pageAnnotations.add(annotation);
			allAnnotations.add(new Pair<Integer, List<String>>(entry.getKey(), pageAnnotations));
		}
		return allAnnotations;
	}

}
