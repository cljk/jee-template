package de.csmp.jeetemplate.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/test"})
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = -385682641759861234L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream os = resp.getOutputStream();
		os.print("test response");
	}

	
}
