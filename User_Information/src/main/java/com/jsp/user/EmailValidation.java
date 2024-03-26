package com.jsp.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.management.loading.PrivateClassLoader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/EmailSubmit")
public class EmailValidation extends HttpServlet{

	String url = "jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
	
	String select = "select * from userinformation where User_EmailId=?";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String emailid = request.getParameter("emailid");
		System.out.println(emailid);
		
		HttpSession session = request.getSession();
		session.setAttribute("mailid", emailid);
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			
			ps.setString(1, emailid);
			ResultSet resultSet = ps.executeQuery();
			
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html");
			
			if (resultSet.next()) {
				
				Random random = new Random();
				int otp = random.nextInt(10000);
				if (otp<1000) {
					otp = otp+1000;
				}
				
				
				session.setMaxInactiveInterval(20);
				RequestDispatcher rd = request.getRequestDispatcher("Password.html");
				rd.forward(request, response);
				
			} else {
				
				RequestDispatcher rd = request.getRequestDispatcher("Email.html");
				rd.include(request, response);
				writer.println("<center><h1>Invalid Email Id...</h1></center>");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
}
}