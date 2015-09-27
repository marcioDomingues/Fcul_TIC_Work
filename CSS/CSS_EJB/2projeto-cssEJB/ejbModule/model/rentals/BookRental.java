package model.rentals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import model.lendables.Lendable;

@Entity
public class BookRental extends Rental {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7170927754164978667L;

	private int lastPageVisited;
	
	@OneToMany(cascade=CascadeType.ALL)
	@MapKeyColumn(name="PAGES_KEY", table="RENTAL_PAGE")
	private Map<Integer, Page> pages;

	BookRental() {}
	
	public BookRental(Lendable book) {
		super(book);
		pages = new TreeMap<Integer, Page> (); 
	}
	
	/**
	 * 
	 * @param pageNum
	 * @param text
	 * @return the number of the new annotation
	 */
	public int addAnnotation(int pageNum, String text) {
		Page page = getCreatePage(pageNum);
		return page.addAnnotation(text);
	}
	
	/**
	 * 
	 * @param pageNum
	 * @param annotationNum
	 * @return the text of the removed annotation, or null if the annotation does not exist
	 */
	public String removeAnnotation(int pageNum, int annotationNum) {
		Page page = pages.get(pageNum);
		return page != null ? page.removeAnnotation(annotationNum) : null;
	}

	public Iterable<String> getAnnotations(int pageNum) {
		Page p = pages.get(pageNum);
		if (p != null) {
			return p.getAnnotations();
		}
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

	public List<Integer> getBookmarkedPageNumbers () {
		LinkedList<Integer> result = new LinkedList<Integer>();
		for (Page page : pages.values())
			if (page.isBookmarked())
				result.add(page.getPageNum());
		return result;
	}

	public boolean toggleBookmark(int pageNum) {
		return getCreatePage(pageNum).toggleBookmark();
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
			page = new Page(pageNum);
			pages.put(pageNum, page);
		}
		return page;
	}

}
