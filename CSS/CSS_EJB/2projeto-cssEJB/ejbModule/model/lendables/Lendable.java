package model.lendables;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import model.EMediumAttribute;
import model.EMediumProperties;
import model.EMediumType;
import model.events.EMediumListener;
import model.rentals.BookRental;
import model.rentals.Rental;
import model.shelves.EMedium;
import persistence.utils.EMediumPropertiesConverter;
import domain.EMediumRemote;

@Entity
@NamedQueries({
	@NamedQuery(name = Lendable.FIND_ALL, query = "SELECT l FROM Lendable l"),
	@NamedQuery(name = Lendable.FIND_BY_ID, query = "SELECT e FROM Lendable e WHERE e.id =:"+Lendable.FIND_ID),
})

public class Lendable implements EMedium, EMediumRemote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2966954329951768673L;
	
	public static final String FIND_ALL = "Lendable.findAll";
	public static final String FIND_ID = "ID";
    public static final String FIND_BY_ID = "Lendable.findByID";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Lob()
	@Basic(fetch=FetchType.LAZY)
	@Column(nullable=false)
	private File file;
	
	@Column(columnDefinition="TEXT NOT NULL")
	@Convert(converter = EMediumPropertiesConverter.class)
	private EMediumProperties properties;
	
	public Lendable() {}
	
	public Lendable(EMediumProperties properties) {
		this.properties = properties.clone();
		this.file = new File(properties.getAttribute(EMediumAttribute.PATH));
	}
	
	public int getId() {
		return this.id;
	}
	
	public File getFile() {
		return file;
	}
	
	public String getPath() {
		return properties.getAttribute(EMediumAttribute.PATH);
	}
	
	public String getTitle() {
		return properties.getAttribute(EMediumAttribute.TITLE);
	}
	
	public String getAuthor() {
		return properties.getAttribute(EMediumAttribute.AUTHOR);
	}

	public String getMimeType() {
		return properties.getAttribute(EMediumAttribute.MIME_TYPE);
	}

	public List<String> getTags() {
		return new LinkedList<String> (properties.getAttribute(EMediumAttribute.TAGS));
	}
	
	public void addEMediumListener(EMediumListener listener) {
    }
	
	public void removeEMediumListener(EMediumListener listener) {
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
		String type = (String)readProperty(EMediumAttribute.MEDIUM_TYPE);
		return EMediumType.valueOf(type.toUpperCase());
	}
	
	public boolean hasLicensesAvailable() {
		return ((Integer)readProperty(EMediumAttribute.LICENSES)) > 0;
	}
	
	// @pre hasLicensesAvailable()
	public void createLending() {
		writeProperty(EMediumAttribute.LICENSES, ((Integer)readProperty(EMediumAttribute.LICENSES)) - 1);
	}
	
	public void revokeLending() {
		writeProperty(EMediumAttribute.LICENSES, ((Integer)readProperty(EMediumAttribute.LICENSES)) + 1);
	}

	private void writeProperty(EMediumAttribute attr, int value) {
		properties = properties.clone(); // Needed so that JPA knows the attribute has changed
		properties.addAttribute(attr, value);
	}

	private Object readProperty(EMediumAttribute attr) {
		return properties.getAttribute(attr);
	}

	@Override
	public EMediumProperties getEMediumProperties() {
		return properties.clone();
	}

	@Override
	public boolean isExpired() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + getPath().hashCode();
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
		if (id != other.id)
			return false;
		if (!getPath().equals(other.getPath()))
			return false;
		return true;
	}

	public Rental makeRental() {
		return getType() == EMediumType.DOCUMENT ?
				new BookRental(this) : new Rental(this);
	}

	@Override
	public Lendable getLendable() {
		return this;
	}

}