package ui;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.ShelvesRemoteFacade;

@WebServlet("/AddNormalShelf")
public class AddShelvesHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private ShelvesRemoteFacade shelves;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String newShelfName = request.getParameter("name");
		try {
			request.setAttribute("result", shelves.addNormalShelf(newShelfName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// request.getRequestDispatcher("").forward(request, response);
		response.sendRedirect("");
	}

}
