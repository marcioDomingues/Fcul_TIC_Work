package model.events;

import java.util.EventObject;

public class EMediaEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 945504911542242329L;
	
	private int pageNum;
	private boolean isBookmarked;
	private int annotationNum;
	private boolean hasAnnotations;
	private String annotationText;
	
	public EMediaEvent (Object document) {
		super (document);
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}

	public boolean isBookmarked() {
		return isBookmarked;
	}

	public void setAnnotationNum(int annotationNum) {
		this.annotationNum = annotationNum;
	}

	public int getAnnotationNum() {
		return annotationNum;
	}

	public void setHasAnnotations(boolean hasAnnotations) {
		this.hasAnnotations = hasAnnotations;
	}

	public boolean isHasAnnotations() {
		return hasAnnotations;
	}

	public void setAnnotationText(String annotationText) {
		this.annotationText = annotationText;
	}

	public String getAnnotationText() {
		return annotationText;
	}
}
