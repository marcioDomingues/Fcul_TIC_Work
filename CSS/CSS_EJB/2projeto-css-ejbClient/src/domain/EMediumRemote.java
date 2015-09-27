package domain;

import javax.ejb.Remote;

@Remote
public interface EMediumRemote {
	
	public int getId();	
	public String getPath();
	public String getTitle();
	public String getAuthor();
	

}
