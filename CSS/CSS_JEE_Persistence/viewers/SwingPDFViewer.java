import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import model.rentals.NoSuchPageException;
import services.viewer.Viewer;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;


public class SwingPDFViewer extends Viewer {

	private transient PDFFile pdffile;
	
	public SwingPDFViewer() {
		super("swing");
	}

	@Override
	public void setDocument (File file) throws IOException {
		super.setDocument(file);
		try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
		    FileChannel channel = raf.getChannel();
		    ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,
		            0, channel.size());
		    pdffile = new PDFFile(buf);
		}
	}
	
	@Override
	protected void loadMimeTypes() {
		List<String> mimeTypes = new ArrayList<String>(1);
		mimeTypes.add("application/pdf");
		setMimeTypes (mimeTypes);
	}

	@Override
	public Object getPage(int pageNum, int width, int height) throws NoSuchPageException {
		if (pageNum < 1 || pageNum > pdffile.getNumPages())
			throw new NoSuchPageException();
		
		PDFPage page = pdffile.getPage(pageNum);
	        		
	    int sourceWidth = (int) page.getBBox().getWidth();
	    int sourceHeight = (int) page.getBBox().getHeight();
	    int thumbWidth, thumbHeight;
	    
	    if (sourceWidth > sourceHeight) {
	    	thumbWidth = width;
	    	thumbHeight = thumbWidth * sourceHeight / sourceWidth;
	    } else {
	    	thumbHeight = height;
	    	thumbWidth = thumbHeight * sourceWidth / sourceHeight;
	    }
	    
	    if (thumbWidth > width) {
	    	thumbWidth = width;
	    	thumbHeight = thumbWidth * sourceHeight / sourceWidth;
	    } else if (thumbHeight > height) {
	    	thumbHeight = height;
	    	thumbWidth = thumbHeight * sourceWidth / sourceHeight;
	    }
	
		final Rectangle2D rect = page.getBBox();
		
		Image img = page.getImage(
				thumbWidth, thumbHeight, //width & height
            	rect, // clip rect
            	null, // null for the ImageObserver
            	true, // fill background with white
            	true  // block until drawing is done
            );
		
	    return img;
	}

	@Override
	public boolean canSlideshow() {
		return true;
	}
}
