package ui;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.EMediumRemote;
import domain.LibraryRemoteFacade;
import domain.ShelvesRemoteFacade;

@WebServlet("") /* HomePage URI */
public class HomePageHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB private ShelvesRemoteFacade shelves;
	@EJB private LibraryRemoteFacade library;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			shelves.startUp(); //to solve EJB missing PostConstruct for non-singleton
			
			request.setAttribute("mainShelf", shelves.getMainShelf());
			request.setAttribute("shelves", shelves.getShelves());
			request.setAttribute("items",  library.getItems());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

}
