package view.swing;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import model.EMedium;
import services.viewer.Viewer;

/**
 * An extended label for showing documents
 * 
 * @author fmartins
 *
 */
public class EMediumLabel extends JLabel {
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -7009999655067511686L;
	
	/**
	 * The document displayed by this label
	 */
	private EMedium eMedium;
	
	private int slideDuration;
	
	private Viewer eMediaViewer;
	
	private JPopupMenu contextMenu;

	/**
	 * Constructs a document label given a image and a document
	 * @param image The image to be shown
	 * @param eMedia The document shown
	 */
	public EMediumLabel(ImageIcon image, EMedium eMedia, Viewer eMediaViewer,
			JPopupMenu contextMenu) {
		super (image);
		this.eMedium = eMedia;
		this.eMediaViewer = eMediaViewer;
		this.setSlideDuration(5);
		this.contextMenu = contextMenu;
	}
	

	/**
	 * @return The document attached to this label
	 */
	public EMedium getEMedium() {
		return eMedium;
	}
	

	/**
	 * @param eMedium Attaches an e-media to this label
	 */
	public void setEMedium(EMedium eMedium) {
		this.eMedium = eMedium;
	}


	public void setSlideDuration(int slideDuration) {
		this.slideDuration = slideDuration;
	}


	public int getSlideDuration() {
		return slideDuration;
	}
	
	public Viewer getEMediumViewer() {
		return eMediaViewer;
	}


	public void setEMediumViewer(Viewer eMediaViewer) {
		this.eMediaViewer = eMediaViewer;
	}
	
	public JPopupMenu getContextMenu() {
		return contextMenu;
	}
}
