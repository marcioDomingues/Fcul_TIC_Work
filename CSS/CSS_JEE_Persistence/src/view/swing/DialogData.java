package view.swing;

import model.EMediumAttribute;
import model.EMediumPropertiesData;

public class DialogData {
	public final EMediumPropertiesData data;
	private boolean accepted;
	
	public DialogData() {
		this.data = new EMediumPropertiesData();
	}
	
	public DialogData(EMediumPropertiesData data) {
		this.data = data;
	}

	public Object getAttribute(EMediumAttribute attribute) {
		return data.getAttribute(attribute);
	}

	public void addAttribute(EMediumAttribute attribute, Object value) {
		data.addAttribute(attribute, value);
	}
	
	public boolean didUserAccept() {
		return accepted;
	}

	public void userAccepted(boolean choice) {
		accepted = choice;
	}
}