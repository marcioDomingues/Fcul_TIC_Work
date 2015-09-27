package model.lendables;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.swing.event.EventListenerList;

import model.EMedium;
import model.EMediumAttribute;
import model.EMediumPropertiesData;
import model.EMediumType;
import model.events.EMediumListener;
import model.transactions.DBHelper;
import model.transactions.PersistentOperation;
import model.transactions.Transactionable;

@Entity
@NamedQueries(
	{ 
		@NamedQuery(name = Lendable.FIND_ALL, query = "SELECT e FROM Lendable e"),
		@NamedQuery(name = Lendable.FIND_BY_ID, query = "SELECT e FROM Lendable e WHERE e.ID =:"+Lendable.FIND_ID),
		@NamedQuery(name = Lendable.FIND_LAST_ADDED, query = "SELECT e FROM Lendable e ORDER BY e.dateAdded DESC")
	}
)

public class Lendable implements EMedium {

	public static final String FIND_ALL = "Lendable.findAll";
	public static final String FIND_BY_ID = "Lendable.findByID";
	public static final String FIND_LAST_ADDED = "Lendable.lastAdded";
	public static final String FIND_ID = "ID";


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int ID;
	@Enumerated
	private EMediumType type;
	@Embedded
	private EMediumPropertiesData properties;
	@Lob
	private File file;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_ADDED")
	private Date dateAdded;
	@Transient
	private int licenses;
	@Transient
	private EventListenerList listeners;

	public Lendable() {}

	public Lendable(EMediumType type, EMediumPropertiesData properties) {
		this.type = type;
		this.dateAdded = new Date();
		this.properties = properties.clone();
		this.licenses = (Integer) properties
				.getAttribute(EMediumAttribute.LICENSES);
		this.file = new File(
				(String) properties.getAttribute(EMediumAttribute.PATH));
		listeners = new EventListenerList();
	}
	
	@PostLoad
	private void initialize() {
		listeners = new EventListenerList();
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
		return new LinkedList<String>(
				(List<String>) properties.getAttribute(EMediumAttribute.TAGS));
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
		updateLicensesWith(-1);
	}
	public void returned() {
		updateLicensesWith(+1);
	}
	
	private void updateLicensesWith( int value ) {
		int newLicenses = getLicenses() + value;
		properties.addAttribute(EMediumAttribute.LICENSES, newLicenses);
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
		return getLicenses() > 0;
	}

	private Integer getLicenses() {
		return (Integer) properties.getAttribute(EMediumAttribute.LICENSES);

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
	
	@Override
	public EMedium getItem() {
		return this;
	}
	
	@Override
	public int getID() {
		return this.ID;
	}
}