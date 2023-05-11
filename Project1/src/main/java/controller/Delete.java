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

public class Delete extends HttpServlet {
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

//		ServletContext j = getServletContext();
//		String k = (String) j.getAttribute("A");
//		System.out.print(j.getAttribute("A")+" "+k);
		Gson gson=new Gson();
		Employee emp=gson.fromJson(request.getReader(), Employee.class);
		PrintWriter out = response.getWriter();

		if(dao.present(emp.getEmployeeId())) {
			dao.deleteDetails(emp.getEmployeeId());
			out.println("Details of ID "+emp.getEmployeeId()+" are deleted");
			DisplayAll d=new DisplayAll();
			d.doPost(request, response);

		}
		else {
			out.println("ID "+emp.getEmployeeId()+" is not present in database to delete");
		
		}
//		dao.dbClose();
		out.close();

		
		



	}

}














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
//public class Delete extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		AllEmployeeDao dao = new AllEmployeeDao();
//		Gson gson=new Gson();
//		Employee emp=gson.fromJson(request.getReader(), Employee.class);
//		PrintWriter out = response.getWriter();
//
//		if(dao.present(emp.getEmployeeId())) {
//			dao.deleteDetails(emp.getEmployeeId());
//			out.println("Details of ID "+emp.getEmployeeId()+" are deleted");
//			DisplayAll d=new DisplayAll();
//			d.doPost(request, response);
//
//		}
//		else {
//			out.println("ID "+emp.getEmployeeId()+" is not present in database to delete");
//		
//		}
//		out.close();
//
//		
//		
//
//
//
//	}
//
//}
