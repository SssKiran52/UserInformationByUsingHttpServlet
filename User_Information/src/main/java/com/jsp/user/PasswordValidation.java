package com.jsp.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PasswordSubmit")
public class PasswordValidation extends HttpServlet{

	String url = "jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
	
	String select = "select * from userinformation where User_Password=? and User_EmailId=?";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String password = request.getParameter("password");
		System.out.println(password);
		
		
		HttpSession session = request.getSession();
		String emailid = (String) session.getAttribute("mailid");
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		
		if (emailid!=null) {
		
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(select);
				
				ps.setString(1, password);
				ps.setString(2, emailid);
				
				ResultSet resultSet = ps.executeQuery();
				
				if (resultSet.next()) {
					
					Random random = new Random();
					int otp = random.nextInt(10000);
					if (otp<1000) {
						otp+=1000;
					}
					session.setMaxInactiveInterval(10);
					RequestDispatcher rd = request.getRequestDispatcher("OTP.html");
					rd.include(request, response);
					writer.println("<center><h1>Your OTP : "+otp+"</h1></center>");
					
					session.setAttribute("gotp", otp);
					
					
				} else {
					
					RequestDispatcher rd = request.getRequestDispatcher("Password.html");
					rd.include(request, response);
					writer.println("<center><h1>Invalid Password...</h1></center>");
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			
			writer.println("<center><h1>Session TimeOut...</h1></center>");
			
		}
		
	}
	
}
