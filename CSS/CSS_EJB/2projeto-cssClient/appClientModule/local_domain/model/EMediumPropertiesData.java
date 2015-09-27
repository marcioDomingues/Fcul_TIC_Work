package local_domain.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EMediumPropertiesData implements Cloneable {

	private Map<EMediumAttribute,Object> attributes;
	private EMediumType type;
	
	public EMediumPropertiesData() {
		attributes = new HashMap<EMediumAttribute,Object>();
	}

	public Object getAttribute(EMediumAttribute attribute) {
		return attributes.get(attribute);
	}

	public void addAttribute(EMediumAttribute attribute, Object value) {
		attributes.put(attribute, value);
	}
	
	public boolean isMediumType(EMediumType type) {
		return this.type == type; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EMediumPropertiesData clone() {
		EMediumPropertiesData newData = null;
		try {
			newData = (EMediumPropertiesData) super.clone();
			newData.attributes = new HashMap<EMediumAttribute, Object>(attributes);
			if (attributes.containsKey(EMediumAttribute.TAGS)) {
				newData.attributes.put(EMediumAttribute.TAGS, 
						new LinkedList<String>((Collection<? extends String>) 
								attributes.get(EMediumAttribute.TAGS)));
			}
		} catch (CloneNotSupportedException e) {
			// never happens
			e.printStackTrace();
		}
		return newData;
	}
}
