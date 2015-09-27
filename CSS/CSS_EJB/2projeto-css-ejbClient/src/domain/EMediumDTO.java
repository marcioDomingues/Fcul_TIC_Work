package domain;

import java.io.Serializable;

public class EMediumDTO implements Serializable, EMediumRemote {

	private static final long serialVersionUID = -1844221569130384784L;
	
	private int id;
	private String path;
	private String title;
	private String author;
	private String thumb;
	
	public EMediumDTO() {}
	
	public EMediumDTO(EMediumRemote item) {
		this.id = item.getId();
		this.path = item.getPath();
		this.title = item.getTitle();
		this.author = item.getAuthor();
	}
	
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	
	

}
