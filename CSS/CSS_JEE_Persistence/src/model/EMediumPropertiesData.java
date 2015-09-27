package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

@Embeddable 
public class EMediumPropertiesData implements Cloneable {
 	@Lob @Basic
	private Map<EMediumAttribute, EMediumPropertyWrapper> attributes;
 	//@Enumerated(EnumType.STRING)
    //private EMediumType type;
	
    public EMediumPropertiesData() {
		attributes = new HashMap<EMediumAttribute,EMediumPropertyWrapper>();
	}

	public Object getAttribute(EMediumAttribute attribute) {
		return attributes.get(attribute).get();
	}

	public void addAttribute(EMediumAttribute attribute, Object value) {
		attributes.put( attribute,new EMediumPropertyWrapper(value));
	}
	
	public boolean isMediumType(EMediumType type) {
		EMediumType selfType = (EMediumType) attributes.get(EMediumAttribute.MEDIUM_TYPE).get();
		return selfType == type; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EMediumPropertiesData clone() {
		 EMediumPropertiesData newData = null;
		try {
			newData = (EMediumPropertiesData) super.clone();
			newData.attributes = new HashMap<EMediumAttribute, EMediumPropertyWrapper>(attributes);
			if (attributes.containsKey(EMediumAttribute.TAGS)) {
				newData.attributes.put(EMediumAttribute.TAGS, 
						new EMediumPropertyWrapper(new LinkedList<String>((Collection<? extends String>) 
								attributes.get(EMediumAttribute.TAGS).get())));
			}
		} catch (CloneNotSupportedException e) {
			// never happens
			e.printStackTrace();
		}
		return newData;
	}

}
