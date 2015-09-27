package local_domain.model.lendables;

import java.io.File;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;

import local_domain.model.EMedium;
import local_domain.model.EMediumAttribute;
import local_domain.model.EMediumPropertiesData;
import local_domain.model.EMediumType;
import local_domain.model.events.EMediumListener;

public class Lendable implements EMedium {

	private EMediumType type;
	private EMediumPropertiesData properties;
	private File file;
	private int licenses;
	private EventListenerList listeners;
		
	public Lendable(EMediumType type, EMediumPropertiesData properties) {
		this.type = type;
		this.properties = properties.clone();
		this.licenses = (Integer) properties.getAttribute(EMediumAttribute.LICENSES);
		this.file = new File((String) properties.getAttribute(EMediumAttribute.PATH));
		listeners = new EventListenerList();
	}
	
	public Lendable(EMediumPropertiesData properties) {
		this.properties = properties.clone();
		this.file = new File(((String)properties.getAttribute(EMediumAttribute.PATH)));
	}
	
	public File getFile() {
		return file;
	}
	
	public String getTitle() {
		return (String) properties.getAttribute(EMediumAttribute.TITLE);
	}
	
	public String getAuthor() {
		return (String) properties.getAttribute(EMediumAttribute.AUTHOR);
	}

	public String getMimeType() {
		return (String) properties.getAttribute(EMediumAttribute.MIME_TYPE);
	}

	@SuppressWarnings("unchecked")
	public List<String> getTags() {
		return new LinkedList<String> ((List<String>) properties.getAttribute(EMediumAttribute.TAGS));
	}
	
	public void addEMediumListener(EMediumListener listener) {
        listeners.add(EMediumListener.class, listener);
    }
	
	public void removeEMediumListener(EMediumListener listener) {
        listeners.remove(EMediumListener.class, listener);
    }

	@Override
	public boolean canBookmarkPage() {
		return false;
	}

	@Override
	public boolean canAnnotate() {
		return false;
	}

	@Override
	public boolean canAnnotatePage() {
		return false;
	}

	@Override
	public EMediumType getType() {
		return type;
	}
	
	// @pre hasLicenses()
	public void rent() {
		licenses--;
		properties.addAttribute(EMediumAttribute.LICENSES, licenses);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + licenses;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lendable other = (Lendable) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public boolean hasLicensesAvailable() {
		return licenses > 0;
	}

	@Override
	public int compareTo(EMedium other) {
		return getTitle().compareTo(other.getTitle());
	}

	@Override
	public EMediumPropertiesData getEMediumProperties() {
		return properties.clone();
	}

	@Override
	public boolean isExpired() {
		return false;
	}

}