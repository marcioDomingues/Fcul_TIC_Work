package model.rentals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.swing.event.EventListenerList;

import model.EMedium;
import model.lendables.Lendable;
import model.transactions.DBHelper;
import adts.Pair;


/*
 * @param 
 */
public class BookRental_RefactorForTests extends Rental {

	private int lastPageVisited_test;
	
	private Map<Integer, Page> pages_test;

	
	public BookRental_RefactorForTests(){};
	
	public BookRental_RefactorForTests(Lendable book) {
		super(book);
		pages_test = new TreeMap<Integer, Page> (); 
	}

	//need this for the tests
	public BookRental_RefactorForTests( Map<Integer, Page> p, Integer i ) {
		
		pages_test = new TreeMap<Integer, Page> (); 
		pages_test = p; 
		lastPageVisited_test = i;
		initialize();
	}

	//como nao uso persistencia 
	//tenho que fazer chamada a este metodo 
	//em todos os metodos que persistiam originalmente
	public void initialize() {
		listeners = new EventListenerList();
		for ( Page page : pages_test.values()) {
			page.setListeners(listeners);
		}
	}
	
	
	///////////////////////////////////////////////////
	///////////////////////////////////////////////////	
	
	
	public void addAnnotation(int pageNum, String text) {
		getCreatePage(pageNum).addAnnotation(text);
		initialize();
	}
	
	public void removeAnnotation(int pageNum, int annotNum) {
		Page page = pages_test.get(pageNum);
		if (page != null) {
			page.removeAnnotation (annotNum);
			initialize();		
			}
	}

	public Iterable<String> getAnnotations(int pageNum) {
		Page p = pages_test.get(pageNum);
		if (p != null) 
			return p.getAnnotations();
		return new LinkedList<String>();
	}

	public String getAnnotationText(int pageNum, int annotNum) {
		Page p = pages_test.get(pageNum);
		return p != null ? p.getAnnotationText(annotNum) : "";
	}

	public boolean hasAnnotations(int pageNum) {
		Page p = pages_test.get(pageNum);
		return p != null && p.hasAnnotations ();
	}
	
	///////////////////////////////////////////////////
	///////////////////////////////////////////////////
	

	public boolean isBookmarked(int pageNum) {
		Page p = pages_test.get(pageNum);
		return p != null && p.isBookmarked();
	}

	public List<Integer> getBookmarks () {
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (Page p : pages_test.values())
			if (p.isBookmark())
				l.add(p.getPageNum());
		return l;
	}

	public void toggleBookmark(int pageNum) {
		getCreatePage(pageNum).toggleBookmark();
		initialize();
	}

	
	
	///////////////////////////////////////////////////
	///////////////////////////////////////////////////
	
	
	public int getLastPageVisited() {
		return lastPageVisited_test;
	}

	public void setLastPageVisited(int lastPageVisited) {
		this.lastPageVisited_test = lastPageVisited;
		initialize();
	}
	
	///////////////////////////////////////////////////
	///////////////////////////////////////////////////
	
	@Override
	public boolean canBookmarkPage() {
		return true;
	}
	
	@Override
	public boolean canAnnotatePage() {
		return true;
	}

	
	
	
	private Page getCreatePage(int pageNum) {
		Page page = pages_test.get(pageNum);
		if (page == null) {
			page = new Page(pageNum, listeners);
			pages_test.put(pageNum, page);
			initialize();
		}
		return page;
	}

	public List<Pair<Integer,List<String>>> getAnnotatins() {
		List<Pair<Integer,List<String>>> allAnnotations = new LinkedList<Pair<Integer, List<String>>>();
		for (Entry<Integer, Page> entry : pages_test.entrySet()) {
			List<String> pageAnnotations = new LinkedList<String>();
			for (String annotation : entry.getValue().getAnnotations())
				pageAnnotations.add(annotation);
			allAnnotations.add(new Pair<Integer, List<String>>(entry.getKey(), pageAnnotations));
		}
		return allAnnotations;
	}

}
