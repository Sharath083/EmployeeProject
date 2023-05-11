package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dao.AllEmployeeDao;
import entity.Employee;

public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String mysql = ""; 
    private String username = ""; 
    private String password = ""; 
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        mysql = getServletContext().getInitParameter("mysql");
        username = getServletContext().getInitParameter("name");
        password = getServletContext().getInitParameter("pass");
			// TODO Auto-generated method stub

		AllEmployeeDao dao=new AllEmployeeDao();
		dao.getConnector(mysql, username, password);
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		Employee emp = gson.fromJson(request.getReader(), Employee.class);

		if(!(dao.present(emp.getEmployeeId()))) {
			dao.createDetails(emp);
			String s = gson.toJson(dao.displayJason1(emp.getEmployeeId()));
//			dao.addToSubTables();
			response.setContentType("application/json");
			out.println("Details of ID "+emp.getEmployeeId()+" are created");
			out.print(s);

		}else {
			out.println("id "+emp.getEmployeeId()+" already exists");
			out.println("Enter new ID");

		}
//		dao.dbClose();
		out.close();
				
	}
}
//	int id=Integer.parseInt(request.getParameter("id"));
//	AllEmployeeDao dao=new AllEmployeeDao();
//	PrintWriter out=response.getWriter();
//	if(!(dao.present(id))) {
//		String name=request.getParameter("name");
//		int salary=Integer.parseInt(request.getParameter("salary"));
//		String type=request.getParameter("type");
//		Employee e=new Employee(id, name, salary, type);
//		dao.createDetails(e);
//		dao.addToSubTables();
//		out.println("Details of id "+id+" are added");
//		DisplayAll d=new DisplayAll();
//		d.doGet(request, response);
//
//
//	}
//	else {
//		out.println("id "+id+" already exists");
//		out.println("Enter new ID");
//
//	}
//	
//	out.close();






//
//
//package controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.employee.methods.AllEmployeeDao;
//import com.employee.methods.Employee;
//import com.google.gson.Gson;
//
//
//public class Insert extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//		AllEmployeeDao dao=new AllEmployeeDao();
//		Gson gson = new Gson();
//		PrintWriter out = response.getWriter();
//		Employee emp = gson.fromJson(request.getReader(), Employee.class);
//
//		if(!(dao.present(emp.getEmployeeId()))) {
//			dao.createDetails(emp);
//			String s = gson.toJson(dao.displayJason1(emp.getEmployeeId()));
////			dao.addToSubTables();
//			response.setContentType("application/json");
//			out.println("Details od ID "+emp.getEmployeeId()+" are created");
//			out.print(s);
//
//		}else {
//			out.println("id "+emp.getEmployeeId()+" already exists");
//			out.println("Enter new ID");
//
//		}
//
//		out.flush();
//				
//	}
////	int id=Integer.parseInt(request.getParameter("id"));
////	AllEmployeeDao dao=new AllEmployeeDao();
////	PrintWriter out=response.getWriter();
////	if(!(dao.present(id))) {
////		String name=request.getParameter("name");
////		int salary=Integer.parseInt(request.getParameter("salary"));
////		String type=request.getParameter("type");
////		Employee e=new Employee(id, name, salary, type);
////		dao.createDetails(e);
////		dao.addToSubTables();
////		out.println("Details of id "+id+" are added");
////		DisplayAll d=new DisplayAll();
////		d.doGet(request, response);
////
////
////	}
////	else {
////		out.println("id "+id+" already exists");
////		out.println("Enter new ID");
////
////	}
////	
////	out.close();
//
//
//}
