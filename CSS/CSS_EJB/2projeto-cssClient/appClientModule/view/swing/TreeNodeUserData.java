package view.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

public class TreeNodeUserData {

	private String caption;
	private JPopupMenu popupMenu;
	private ActionListener action;
	
	public TreeNodeUserData(String caption, JPopupMenu popupMenu, ActionListener action) {
		this.caption = caption;
		this.popupMenu = popupMenu;
		this.action = action;
	}

	@Override
	public String toString() {
		return caption;
	}

	public JPopupMenu getContextMenu() {
		return popupMenu;
	}

	public String getCaption() {
		return caption;
	}
	
	public void doAction(Object source) {
		if (action != null)
			action.actionPerformed(new ActionEvent(source, 
				ActionEvent.ACTION_PERFORMED, caption + " tree option selected"));
	}
	 
}
