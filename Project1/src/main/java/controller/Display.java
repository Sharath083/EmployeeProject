package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dao.AllEmployeeDao;
import entity.Employee;
public class Display extends HttpServlet {
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
		
		Employee emp = gson.fromJson(request.getReader(), Employee.class);

		Hashtable<Integer,Employee> e = dao.displayJason1(emp.getEmployeeId());
		String s = gson.toJson(e);
		
//		List<Employee> e = dao.displayEmp(id);
//		String s = gson.toJson(e);
		PrintWriter out = response.getWriter();

		if(!e.isEmpty()) {
			try {
				response.setContentType("application/JSON");
				response.setCharacterEncoding("UTF-8");
				out.println(s);
			}
			finally {
//				dao.dbClose();
				out.close();
			}
		}
		else {
			out.println("The employe with "+emp.getEmployeeId()+" is not present");
//			dao.dbClose();
			out.close();

		}

	}


}



//package controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Hashtable;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.employee.methods.AllEmployeeDao;
//import com.google.gson.Gson;
//import com.employee.methods.Employee;
//
//public class Display extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		AllEmployeeDao dao = new AllEmployeeDao();
//		Gson gson = new Gson();
//		
//		Employee emp = gson.fromJson(request.getReader(), Employee.class);
//
//		Hashtable<Integer,Employee> e = dao.displayJason1(emp.getEmployeeId());
//		String s = gson.toJson(e);
//		
////		List<Employee> e = dao.displayEmp(id);
////		String s = gson.toJson(e);
//		PrintWriter out = response.getWriter();
//
//		if(!e.isEmpty()) {
//			try {
//				response.setContentType("application/JSON");
//				response.setCharacterEncoding("UTF-8");
//				out.println(s);
//			}
//			finally {
//				out.close();
//			}
//		}
//		else {
//			out.println("The employe with "+emp.getEmployeeId()+" is not present");
//			out.close();
//
//		}
//
//	}
//
//
//}
