package ui;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.EMediumRemote;
import domain.ShelvesRemoteFacade;

@WebServlet("/RetrieveItemsHandler")
public class RetrieveItemsHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	 
	@EJB
	private ShelvesRemoteFacade shelves;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String ownerName = request.getParameter("name");
		try {
			request.setAttribute("owner", ownerName);
			Iterable<EMediumRemote> items = shelves.getShelfRentals(ownerName);
			request.setAttribute("items", items);
		} catch (Exception e) {
			request.setAttribute("owner", "Error!");
			e.printStackTrace();
		}
		
		//dispatches
		request.getRequestDispatcher("itemsGrid.jsp").forward(request, response);
	}

}
