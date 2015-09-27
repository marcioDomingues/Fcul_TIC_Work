package model.rentals;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.swing.event.EventListenerList;

import model.EMedium;
import model.EMediumPropertiesData;
import model.EMediumType;
import model.events.EMediumListener;
import model.lendables.Lendable;
import model.transactions.DBHelper;
import adts.Pair;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQueries({
		@NamedQuery(name = Rental.FIND_ALL, query = "SELECT r FROM Rental r"),
		@NamedQuery(name = Rental.FIND_BY_ID, query = "SELECT r FROM Rental r WHERE r.ID = :ID"),
		@NamedQuery(name = Rental.FIND_BY_LENDABLE, 
			query = "SELECT r FROM Rental r WHERE r.lendable = :"+ Rental.FIND_LENDABLE) 
})
public class Rental implements EMedium {

	// to search
	public static final String FIND_ALL = "Rental.findAll";
	public static final String FIND_BY_ID = "Rental.findByID";
	public static final String FIND_BY_LENDABLE = "Rental.findByLendable";
	public static final String FIND_LENDABLE = "Lendable";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int ID;

	// saves the ID of the correspondent Lendable
	private Lendable lendable;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_RENTED")
	private Date timestamp;

	@Convert
	private boolean expired;
	@Convert
	private boolean isRented;

	// Element Collection
	@ElementCollection
	private List<String> annotations;

	@Transient
	protected EventListenerList listeners;

	// JPA is hungry
	public Rental() {
	}

	public Rental(Lendable lendable) {
		this.lendable = lendable;
		timestamp = new Date();
		annotations = new LinkedList<String>();
		listeners = new EventListenerList();
		expired = false;
		isRented = true;
	}

	@PostLoad
	public void initialize() {
		listeners = new EventListenerList();
	}

	public Lendable getLendable() {
		return this.lendable;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public String getAuthor() {
		return lendable.getAuthor();
	}

	public Date getRentalTimestamp() {
		return timestamp;
	}

	public void addAnnotation(String text) {
		System.out.println("Rental - addAnnotation");
		annotations.add(text);
		DBHelper.INSTANCE.update(this);
	}

	// @requires a valid annotNum
	public void removeAnnotation(int annotNum) {
		if (annotNum >= 0 && annotNum < annotations.size()) {
			annotations.remove(annotNum);
			DBHelper.INSTANCE.update(this);
		}
	}

	public Iterable<String> getAnnotations(int pageNum) {
		return annotations;
	}

	public List<Pair<Integer, List<String>>> getAnnotatins() {
		List<Pair<Integer, List<String>>> allAnnotations = new LinkedList<Pair<Integer, List<String>>>();
		allAnnotations.add(new Pair<Integer, List<String>>(1, annotations));
		return allAnnotations;
	}

	public String getAnnotationText(int annotNum) {
		return annotations.get(annotNum);
	}

	public boolean hasAnnotations(int pageNum) {
		return !annotations.isEmpty();
	}

	public File getFile() {
		return lendable.getFile();
	}

	public String getMimeType() {
		return lendable.getMimeType();
	}

	@Override
	public String getTitle() {
		return lendable.getTitle();
	}

	@Override
	public List<String> getTags() {
		return lendable.getTags();
	}

	@Override
	public void addEMediumListener(EMediumListener listener) {
		listeners.add(EMediumListener.class, listener);
	}

	@Override
	public void removeEMediumListener(EMediumListener listener) {
		listeners.remove(EMediumListener.class, listener);
	}

	@Override
	public boolean canBookmarkPage() {
		return false;
	}

	@Override
	public boolean canAnnotate() {
		return true;
	}

	@Override
	public boolean canAnnotatePage() {
		return false;
	}

	@Override
	public EMediumType getType() {
		return lendable.getType();
	}

	@Override
	public int compareTo(EMedium o) {
		return lendable.compareTo(o);
	}

	@Override
	public EMediumPropertiesData getEMediumProperties() {
		return lendable.getEMediumProperties();
	}

	public void renew() {
		timestamp = new Date();
		expired = false;
		isRented = true;
	}

	public void returnRental() {
		isRented = false;
	}

	@Override
	public boolean isExpired() {
		return expired;
	}

	public boolean isRented() {
		return isRented;
	}

	@Override
	public EMedium getItem() {
		return this.lendable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result
				+ ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + (expired ? 1231 : 1237);
		result = prime * result
				+ ((lendable == null) ? 0 : lendable.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + (isRented ? 1231 : 1237);
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
		Rental other = (Rental) obj;
		if (ID != other.ID)
			return false;
		if (annotations == null) {
			if (other.annotations != null)
				return false;
		} else if (!annotations.equals(other.annotations))
			return false;
		if (expired != other.expired)
			return false;
		if (lendable == null) {
			if (other.lendable != null)
				return false;
		} else if (!lendable.equals(other.lendable))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (isRented != other.isRented)
			return false;
		return true;
	}

}
