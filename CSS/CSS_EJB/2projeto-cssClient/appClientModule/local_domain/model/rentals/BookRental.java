package local_domain.model.rentals;

import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import local_domain.adts.Pair;
import local_domain.model.lendables.Lendable;

public class BookRental extends Rental {

	private int lastPageVisited;
	private Map<Integer, Page> pages;

	public BookRental(Lendable book) {
		super(book);
		pages = new TreeMap<Integer, Page> (); 
	}
	
	public void addAnnotation(int pageNum, String text) {
		getCreatePage(pageNum).addAnnotation(text);
	}
	
	public void removeAnnotation(int pageNum, int annotNum) {
		Page page = pages.get(pageNum);
		if (page != null) {
			page.removeAnnotation (annotNum);
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
	}

	public int getLastPageVisited() {
		return lastPageVisited;
	}

	public void setLastPageVisited(int lastPageVisited) {
		this.lastPageVisited = lastPageVisited;
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
