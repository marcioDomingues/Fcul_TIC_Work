package view.swing;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import model.EMedium;
import model.rentals.NoSuchPageException;
import services.viewer.Viewer;
import services.viewer.ViewerFactory;

final class ThumbnailFactory {

	/**
	 * Thumbnail size
	 */
	public static final int THUMBNAIL_SIZE = 215;
	
	private static ImageIcon NO_VIEWER_IMAGE = new ImageIcon ("images/no_viewer_available.png");
	
	private static ThumbnailFactory instance;
	
	public static ThumbnailFactory getInstance () {
		if (instance == null)
			instance = new ThumbnailFactory();
		return instance;
	}
	
	private Map<EMedium, Thumbnail> thumbnails;
	
	private ThumbnailFactory () {
		thumbnails = new HashMap<EMedium, Thumbnail> ();
	}
	
	public Thumbnail getThumbnail (EMedium eMedia) throws IOException, NoSuchPageException {
		File dFile = eMedia.getFile();
		File tbFile = new File(System.getProperty("user.dir") + File.separator + "docs" + 
				File.separator + "thumbnails" + File.separator + "thumb_" + dFile.getName());
		Viewer v = ViewerFactory.getInstance().getViewer(eMedia.getMimeType(), "swing");
		if (!tbFile.exists() || dFile.lastModified() > tbFile.lastModified()) {
			Thumbnail tb;
			if (v != null) {
				v.setDocument(dFile);
				tb = new Thumbnail (tbFile, new ImageIcon((Image) v.getPage(1, THUMBNAIL_SIZE, THUMBNAIL_SIZE)), v);
			} else 
				tb = new Thumbnail (tbFile, NO_VIEWER_IMAGE, null);
			tb.save();
			thumbnails.put(eMedia, tb);
		} 
		
		if (thumbnails.get(eMedia) == null) {
			Thumbnail tb = Thumbnail.load(tbFile);
			tb.setViewer(v);
			thumbnails.put(eMedia, tb);
		}
		
		return thumbnails.get(eMedia);
	}
}
