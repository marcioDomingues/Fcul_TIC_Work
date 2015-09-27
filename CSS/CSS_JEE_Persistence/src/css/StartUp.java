package css;

import java.io.IOException;

import model.CSS;
import view.StartupUI;

/**
 * The main project class
 * 
 * @author fmartins
 *
 */
public class StartUp {
	
	public static void main(String[] args) throws IOException {
		CSS lei = new CSS();

		StartupUI.run(new CSSBookshelfUIDelegate(lei.shelvesHandler, lei.libraryHandler), 
				new CSSEMediaUIDelegate(), new CSSEMediumMetadataUIDelegate());
	}
}
