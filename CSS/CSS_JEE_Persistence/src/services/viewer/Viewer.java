package services.viewer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.rentals.NoSuchPageException;

/**
 * Represents a viewer
 * 
 * @author fmartins
 *
 */
public abstract class Viewer {
	
	private List<String> supportedViewerMimeTypes;
	private String widgetToolkit;
	protected File file;
	
	public Viewer (String widgetToolkit) {
		this.widgetToolkit = widgetToolkit;
		loadMimeTypes();  // Mind the application of template method!
	}

	protected abstract void loadMimeTypes();

	protected void setMimeTypes(List<String> mimeTypes) {
		this.supportedViewerMimeTypes = new LinkedList<String> (mimeTypes);
	}
	
	public Iterable<String> getSupportedViewerMimeTypes() {
		return supportedViewerMimeTypes;
	}

	public String getWidgetToolkit() {
		return widgetToolkit;
	}
		
	public void setDocument (File file) throws IOException {
		this.file = file;
	}

	public abstract Object getPage(int pageNum, int width, int height) throws NoSuchPageException;
	
	public abstract boolean canSlideshow();
}
