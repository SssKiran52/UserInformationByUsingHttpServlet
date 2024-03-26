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

@WebServlet("/UserInfo")
public class UserDetails extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
		
		String emailid = (String) request.getSession().getAttribute("mail");
//		HttpSession session = request.getSession();
//		String emailid = (String) session.getAttribute("mail");
		
		String update = "update userregistration set UserFirstName=?, UserLastName=?, UserMobileNumber=?, UserGender=?, UserAddress=? where UserEmailId=?";
		
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String mobileNumber = request.getParameter("MobileNumber");
		String gender = request.getParameter("Gender");
		String address = request.getParameter("Address");
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(mobileNumber);
		System.out.println(gender);
		System.out.println(address);
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(update);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, mobileNumber);
			ps.setString(4, gender);
			ps.setString(5, address);
			ps.setString(6, emailid);
			
			int result = ps.executeUpdate();
			System.out.println(result);
			
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html");
			
			if (result!=0) {
				
				writer.println("<center><h1>Registration Successfull...</h1></center>");
				
				String select = "select * from teca52.userregistration where emailid=?";
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection1 = DriverManager.getConnection(url);
				PreparedStatement ps1 = connection1.prepareStatement(select);
				ps1.setString(1, emailid);
				
				
			} else {
				
				RequestDispatcher rd = request.getRequestDispatcher("UserInfo.html");
				rd.include(request, response);
				writer.println("<center><h1>Invalid Details</h1></center>");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
