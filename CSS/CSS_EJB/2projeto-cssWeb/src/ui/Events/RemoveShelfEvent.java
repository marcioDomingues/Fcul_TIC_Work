package ui.Events;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.ShelvesRemoteFacade;

@WebServlet("/RemoveShelf")
public class RemoveShelfEvent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ShelvesRemoteFacade shelves;
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String newShelfName = request.getParameter("name");
		try {
			shelves.removeShelf(newShelfName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
