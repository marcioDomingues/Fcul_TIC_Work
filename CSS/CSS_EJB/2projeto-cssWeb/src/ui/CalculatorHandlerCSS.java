package ui;

import java.io.IOException;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.CalculatorRemoteCSS;

@WebServlet("/CalculatorHandlerCSS")
public class CalculatorHandlerCSS extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CalculatorRemoteCSS calc;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		int a = Integer.parseInt(request.getParameter("a"));
		int b = Integer.parseInt(request.getParameter("b"));
		request.setAttribute("result", calc.produtoCSS(a, b));
		request.getRequestDispatcher("result.jsp").forward(request, response);
	}

}
