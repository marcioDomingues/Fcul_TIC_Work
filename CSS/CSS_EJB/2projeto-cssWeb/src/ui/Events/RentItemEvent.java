package ui.Events;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.EMediumDTO;
import domain.ShelvesRemoteFacade;

@WebServlet("/RentItemEvent")
public class RentItemEvent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ShelvesRemoteFacade shelves;
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		EMediumDTO lendableDTO = new EMediumDTO();
		lendableDTO.setId(itemId);
		try {
			request.setAttribute("result", shelves.addOrRenewRental(lendableDTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("");
	}

}
