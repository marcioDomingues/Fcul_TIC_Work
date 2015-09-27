package local_domain.model;

public enum EMediumType {
	DOCUMENT("Document"), SONG("Song"), IMAGE("Image");

	private String description;

	private EMediumType (String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
