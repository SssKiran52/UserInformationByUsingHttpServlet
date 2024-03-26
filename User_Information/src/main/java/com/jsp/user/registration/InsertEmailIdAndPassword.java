package com.jsp.user.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EmailIdAndPassword")
public class InsertEmailIdAndPassword extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
		
		String insert = "insert into userregistration(UserEmailId, UserPassword) values(?,?)";
		
		String emailid = request.getParameter("EmailId");
		String password = request.getParameter("Password");
		
		System.out.println(emailid);
		System.out.println(password);
		
		HttpSession session = request.getSession();
		session.setAttribute("mail", emailid);
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(insert);
			ps.setString(1, emailid);
			ps.setString(2, password);
			int result = ps.executeUpdate();
			System.out.println(result);
			
			if (result!=0) {
				
				RequestDispatcher rd = request.getRequestDispatcher("UserInfo.html");
				rd.forward(request, response);
				
			} else {
				
				RequestDispatcher rd = request.getRequestDispatcher("EmailIdPassword.html");
				rd.include(request, response);
				writer.println("<center><h1>Invalid Details</h1></center>");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
