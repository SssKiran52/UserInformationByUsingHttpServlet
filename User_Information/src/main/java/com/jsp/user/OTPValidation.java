package com.jsp.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/otp")
public class OTPValidation extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String temp = request.getParameter("otp");
		int userotp = Integer.parseInt(temp);
		
		HttpSession session = request.getSession();
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		
		try {
			
			int otp = (int) session.getAttribute("gotp");
			
			if (userotp == otp) {
				
				writer.println("<center><h1>Login Successfull...</h1></center>");
				
			} else {

				RequestDispatcher rd = request.getRequestDispatcher("Password.html");
				rd.include(request, response);
				writer.println("<center><h1>Invalid OTP...</h1></center>");
				
			}
			
		} catch (Exception e) {

			RequestDispatcher rd = request.getRequestDispatcher("Email.html");
			rd.include(request, response);
			writer.println("<center><h1>Session TimeOut...</h1></center>");
			
		}
		
	}
	
}
