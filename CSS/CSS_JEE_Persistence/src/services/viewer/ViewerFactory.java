package services.viewer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public class ViewerFactory {

	private static ViewerFactory instance;
	
	public static ViewerFactory getInstance () {
		if (instance == null)
			instance = new ViewerFactory();
		return instance;
	}
	
	private Map<String, Class<Viewer>> viewers;
	
	private ViewerFactory () {
		viewers = new HashMap<String, Class<Viewer>> ();
		loadViewers();
	}
	
	@SuppressWarnings("unchecked")
	private void loadViewers() {
		// add filters in the filters folder
		File filtersFolder = new File("viewers");
		File [] classes = filtersFolder.listFiles(new FilenameFilter () {
			public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});
		for (File className : classes) {
			try {
				String s = className.getName();
				Class<Viewer> viewerClass = (Class<Viewer>) Class.forName(s.substring(0, s.lastIndexOf('.')));
				Viewer viewer = viewerClass.newInstance();
				for (String mimeType : viewer.getSupportedViewerMimeTypes())
					viewers.put(mimeType+viewer.getWidgetToolkit(), viewerClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// Do nothing! Just ignore the class;
			} catch (SecurityException e) {
				// Do nothing! Just ignore the class;
			} catch (IllegalArgumentException e) {
				// Do nothing! Just ignore the class;
			} catch (InstantiationException e) {
				// Do nothing! Just ignore the class;
			} catch (IllegalAccessException e) {
				// Do nothing! Just ignore the class;
			} catch (ClassCastException e) {
				// Do nothing! Just ignore the class;
			}	
		}

	}

	/**
	 * Gets a viewer for this type of e-media, for a specific
	 * toolkit (in this case, swing)
	 * 
	 * @param rentalMime The mime type of the rental
	 * @param widgetToolkit The UI toolkit 
	 * @return a viewer that matches both requirements or null otherwise.
	 */
	public Viewer getViewer (String documentMime, String widgetToolkit) {
		try {
			Class<Viewer> v = viewers.get(documentMime+widgetToolkit);
			if (v != null)
				return v.newInstance();
			else
				return null;
		} catch (InstantiationException e) { // Never happens
			e.printStackTrace();
		} catch (IllegalAccessException e) { // Never happens
			e.printStackTrace();
		}
		return null;
	}
	
}
