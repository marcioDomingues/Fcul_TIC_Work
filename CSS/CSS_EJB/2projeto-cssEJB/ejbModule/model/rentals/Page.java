package model.rentals;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Page {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false)
	private boolean bookmark;
	
	@ElementCollection
	private List<String> annotations;
	
	@Column(nullable=false)
	private int pageNum;
	
	Page () {
	}
	
	public Page (int pageNum) {
		annotations = new LinkedList<String>();
		this.pageNum = pageNum;
	}
	
	public int addAnnotation (String text) {
		annotations.add(text);
		return annotations.size();
	}
	
	public Iterable<String> getAnnotations () {
		return annotations;
	}
	
	public String getAnnotationText (int annotNum) {
		return annotations.get(annotNum);
	}

	public boolean isBookmarked() {
		return bookmark;
	}

	public boolean toggleBookmark() {
		bookmark = !bookmark;
		return bookmark;
	}

	public int getPageNum() {
		return pageNum;
	}

	public String removeAnnotation(int annotNum) {
		return annotations.remove(annotNum);	
	}

	public boolean hasAnnotations() {
		return annotations.size() > 0;
	}

}
