package css;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.ImageIcon;

public enum AppProperties {
	INSTANCE;
	
	public final String APP_ROOT_NAME;
	public final String LIBRARY_NAME;
	public final String  RENTALS_SHELF_NAME;
	public final String  RENTALS_SHELF_GROUP_NAME;
	public final String RECENTLY_BORROWED_SHELF_NAME;
	public final int APP_START_X;
	public final int APP_START_Y;
	public final int APP_START_WIDTH;
	public final int APP_START_HEIGHT;
	public final int DIVIDER_OFFSET;
	public final int E_MEDIA_GAP_SIZE;
	public final Color E_MEDIA_BACKGROUND_COLOR;
	public final ImageIcon BOOKMARK_ICON_TRUE;
	public final ImageIcon BOOKMARK_ICON_FALSE;
	public final ImageIcon PAGEINFO_ICON_TRUE;
	public final ImageIcon PAGEINFO_ICON_FALSE;
	public final ImageIcon FULLSCREEN_ICON;
	public final ImageIcon CLOSE_ICON;

	private Properties appProperties;
	
	AppProperties() {
		String propertiesFileName = "app.properties";
		appProperties = new Properties();
		try {
			appProperties.load(new FileInputStream(propertiesFileName));
		} catch (Exception e) {
			// It was not able to load properties file.
			// Bad luck, proceed with the default values
		}
		APP_ROOT_NAME = parseString("app_root_name", "Lending E-media Initiative");
		LIBRARY_NAME = parseString("library_name", "Library");
		RENTALS_SHELF_NAME = parseString("rentals_shelf", "My Rentals");
		RENTALS_SHELF_GROUP_NAME = parseString("rentals_shelf_group", "Rentals");
		RECENTLY_BORROWED_SHELF_NAME = parseString("recently_shelf", "Recently Borrowed");

		APP_START_X = parseInt("app_window_start_x", 100);
		APP_START_Y = parseInt("app_window_start_y", 100);
		APP_START_WIDTH = parseInt("app_window_start_width", 850);
		APP_START_HEIGHT = parseInt("app_window_start_height", 600);
		DIVIDER_OFFSET = parseInt("app_window_divider_offset", 180);

		E_MEDIA_GAP_SIZE = parseInt("e_media_gap_size", 40);
		E_MEDIA_BACKGROUND_COLOR = new Color(
				parseInt("e_media_background_color_red", 103),
				parseInt("e_media_background_color_green", 103),
				parseInt("e_media_background_color_blue", 103));

		BOOKMARK_ICON_TRUE = new ImageIcon(parseString("bookmark_icon_true", "images/bookmarkTrue.png"));
		BOOKMARK_ICON_FALSE = new ImageIcon(parseString("bookmark_icon_false", "images/bookmarkFalse.png"));
		PAGEINFO_ICON_TRUE = new ImageIcon(parseString("pageinfo_icon_true", "images/pageInfoTrue.png"));
		PAGEINFO_ICON_FALSE = new ImageIcon(parseString("pageinfo_icon_false", "images/pageInfoFalse.png"));
		FULLSCREEN_ICON = new ImageIcon(parseString("fullscreen_icon", "images/fullscreen.png"));
		CLOSE_ICON = new ImageIcon(parseString("close_icon", "images/close.png"));
	}

	private int parseInt(String property, int defaultValue) {
		try {
			return Integer.parseInt(appProperties.getProperty(property));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	private String parseString(String property, String defaultValue) {
		try {
			return appProperties.getProperty(property);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

}
