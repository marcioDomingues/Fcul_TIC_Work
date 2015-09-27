package view;

import javax.swing.JFrame;

import model.EMedium;

/**
 * @author fmartins
 *
 */
@SuppressWarnings("serial")
public abstract class IBookshelfUI extends JFrame {

	public abstract void addShelfTreeNode(String shelfName);
	
	public abstract void removeShelfTreeNode(String name);
	
	public abstract void addToEMediaPanel (String target, EMedium eMedia);

	public abstract void removeEMediumFromPanel(EMedium eMedium);

	public abstract void showEMedia(String target, Iterable<EMedium> eMedia);
	
	public abstract void start();
}