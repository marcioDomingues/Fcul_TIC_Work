import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import model.rentals.NoSuchPageException;
import services.viewer.Viewer;

public class SwingImageViewer extends Viewer {

	public SwingImageViewer() {
		super("swing");
	}
	
	@Override
	protected void loadMimeTypes() {
		List<String> mimeTypes = new ArrayList<String>(1);
		mimeTypes.add("image/jpg");
		mimeTypes.add("image/png");
		mimeTypes.add("image/gif");
		setMimeTypes (mimeTypes);
	}
	
	@Override
	public void setDocument (File file) throws IOException {
		super.setDocument(file);
	}

	@Override
	public Object getPage(int pageNum, int width, int height) throws NoSuchPageException {
		if (pageNum != 1)
			throw new NoSuchPageException();
		
		Image image = new ImageIcon (file.getAbsolutePath()).getImage();
		
	    double sourceWidth = image.getWidth(null);
	    double sourceHeight = image.getHeight(null);

	    double maxRatio = Math.min(Math.min (width/sourceWidth, height/sourceHeight), 1);

	    int thumbWidth = (int) (sourceWidth * maxRatio);
	    int thumbHeight = (int) (sourceHeight * maxRatio);
	    			
	    return image.getScaledInstance(thumbWidth, thumbHeight, Image.SCALE_SMOOTH);
	}

	@Override
	public boolean canSlideshow() {
		return false;
	}
}
