package Dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import entity.Employee;
public class AllEmployeeDao {
	
//	private final String mysql = "jdbc:mysql://localhost:3306/employee";
//	private final String name = "root";
///	private final String pass = "root";

	
	private final String insertQuery ="INSERT INTO new_table(employeeId, salary, employeeName,employeeType) VALUES (?, ?, ?,?)";
	private final String updateQuery="UPDATE new_table SET   employeeName=? ,salary=? , employeeType=? where employeeId=?";
//	private final String deleteQuery="DELETE from new_table where employeeId=?";
//	private final String trun="Truncate TABLE new_table";
//	
//	private final String truncontract="Truncate TABLE contract";
//	private final String trunparttime="Truncate TABLE parttime";
//	private final String trunpermanent="Truncate TABLE permenant";
	
//	private final String perm="INSERT INTO permenant(employeeId,salary,employeeName)SELECT employeeId,salary, employeeName FROM new_table WHERE employeeType='permenant'";
//	private final String contr="INSERT INTO contract(employeeId,salary, employeeName)SELECT employeeId,salary, employeeName FROM new_table WHERE employeeType='contract'";
//	private final String part="INSERT INTO parttime(employeeId,salary, employeeName)SELECT employeeId,salary, employeeName FROM new_table WHERE employeeType='parttime'";
//	
	private final String jasonQuery = "Select * from new_table";
	private final String queryCheck = "Select employeeType from new_table WHERE employeeId = ?";
	
//	private Connection dbConnection = getConnector();
	static Logger log = Logger.getLogger("Connector");
    static FileHandler fh; 
    String mysql, pass,name;
	public Connection getConnector(String mysql1,String name1,String pass1) {
		mysql=mysql1;
		name=name1;
		pass=pass1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbConnection = DriverManager.getConnection(mysql,name,pass);
			return dbConnection;				
		} catch (Exception e) {
			System.out.println("The Details u have given for data base connection are incorrect");
		}
		return null;		
	}
	 
	public void createDetails(Employee e)   {
		PreparedStatement pstmt=null;
		Connection dbConnection = getConnector(mysql, name, pass);

		try {
			pstmt = dbConnection.prepareStatement(insertQuery);
		    pstmt.setInt(1, e.getEmployeeId());
		    pstmt.setInt(2, e.getSalary());
		    pstmt.setString(3, e.getEmployeeName());
		    pstmt.setString(4, e.getEmployeeType());
		    pstmt.execute();		    
		    subTables(e.getEmployeeType(), e.getEmployeeId());		    
			fh = new FileHandler("D:\\logging.txt");  
	        log.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
		    log.info("Details of ID "+e.getEmployeeId()+" are added to database");
		}catch (Exception e1) {
			log.info("This Employee with ID "+e.getEmployeeId()+ " Already Exist in DataBase");
			System.out.println("Enter New ID");
		}
		finally {
			try {
				pstmt.close();
				dbConnection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	public void updateDetails(Employee e)    {
		Connection dbConnection = getConnector(mysql,name, pass);
		 try{
			String queryChec = "SELECT  employeeType from new_table WHERE employeeId = ?";

			PreparedStatement pst = dbConnection.prepareStatement(queryChec);
			pst.setInt(1, e.getEmployeeId());		
			ResultSet resultSet = pst.executeQuery();
			String typeB = null;
			if(resultSet.next()) {
				typeB=resultSet.getString("employeeType");
			
			System.out.println("Update query");
			PreparedStatement pstmt1 = dbConnection.prepareStatement(updateQuery);
			try {
					
				pstmt1.setString(1, e.getEmployeeName());
				pstmt1.setInt(2, e.getSalary());
				pstmt1.setString(3, e.getEmployeeType());
				pstmt1.setInt(4, e.getEmployeeId());

				pstmt1.execute();
					
				delete(typeB,e.getEmployeeId());
				String type=e.getEmployeeType();								
				subTables( type,e.getEmployeeId());

					
					
					
				
				System.out.println("Details of "+e.getEmployeeId()+" are updated in database");
			} 
			catch (Exception e2) {
				System.out.println("in Exception e2");
				System.out.println(e2);
			}
			finally {
				pstmt1.close();
				pst.close();
				resultSet.close();
				dbConnection.close();

			}
		}
			else {
		        log.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);

				log.info("The id "+e.getEmployeeId()+" you have entered doesnot Exist to update");
			}
		} catch (SQLException e1) {
			System.out.println("in e1");
			System.out.println(e1);
		}										   
	}	
	
	
	public void subTables(String type,int id)  {
		Connection dbConnection = getConnector(mysql,name, pass);
		System.out.println("in sub");
		try {
			if(type.equals("parttime")) {

				String partQ="INSERT INTO parttime(employeeId,salary,employeeName)SELECT employeeId,salary, employeeName FROM new_table WHERE employeeId=?";
				PreparedStatement part1=dbConnection.prepareStatement(partQ);
				part1.setInt(1, id);
				part1.execute();
				part1.close();
				System.out.println("Deleted part");
			}
			else if(type.equals("contract")) {
				String conQ="INSERT INTO contract(employeeId,salary,employeeName)SELECT employeeId,salary, employeeName FROM new_table WHERE employeeId=?";
				PreparedStatement con1=dbConnection.prepareStatement(conQ);
				con1.setInt(1, id);
				con1.execute();	
				con1.close();

			}
			else  {
				System.out.println("perm");
				System.out.println("bfhahjf");
				String permQ="INSERT INTO permenant(employeeId,salary,employeeName)SELECT employeeId,salary, employeeName FROM new_table WHERE employeeId=?";
				PreparedStatement per1=dbConnection.prepareStatement(permQ);
				per1.setInt(1,id);
				per1.execute();
				per1.close();
				System.out.println("Deleted perm");

			}					

		} catch (Exception e) {
			System.out.println(e);
				
		}
		finally {
			try {
				dbConnection.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


	}
	
	public void delete(String s,int id)  {
		Connection dbConnection = getConnector(mysql,name, pass);

		PreparedStatement part=null;
		PreparedStatement con=null;
		PreparedStatement per=null;

		try {
			if(s.equals("parttime")) {
				String ptQuery="DELETE from parttime where employeeId=?";
				part=dbConnection.prepareStatement(ptQuery);
				part.setInt(1, id);
				part.execute();
				part.close();
				System.out.println("Deleted part");
			}
			else if(s.equals("contract")) {
				String cQuery="DELETE from contract where employeeId=?";
				con=dbConnection.prepareStatement(cQuery);
				con.setInt(1, id);
				con.execute();	
				con.close();
				System.out.println("Deleted contract");

			}
			else  {
				String pnQuery="DELETE from permenant where employeeId=?";
				per=dbConnection.prepareStatement(pnQuery);
				per.setInt(1, id);
				per.execute();
				per.close();
				System.out.println("Deleted perm");

			}					

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteDetails(int id)  {
		Connection dbConnection = getConnector(mysql,name, pass);
		try {
			PreparedStatement ps = dbConnection.prepareStatement(queryCheck);
			String deleteQuery="DELETE from new_table where employeeId=?";
			PreparedStatement pstmtDel = dbConnection.prepareStatement(deleteQuery);
			ps.setInt(1, id);
			pstmtDel.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			try {
				String s = null;				
				if(resultSet.next()) {
					s=resultSet.getString("employeeType");
					System.out.println("got "+s);
					pstmtDel.execute();
					delete(s, id);	
					System.out.println("The id "+id+"  in Database are delete");
				}
				else {
					log.info("The id "+id+" doesnot exists in Database to delete");
				}				
			}finally {
				ps.close();
				resultSet.close();
				pstmtDel.close();
				dbConnection.close();

			}

		}
		 catch (Exception e) {
				
		}							
	}


	
	
	
	
	
	
//	public void truncateSub()  {
//		Connection dbConnection = getConnector(mysql,name, pass);
//
//		PreparedStatement pstmt =null;
//		PreparedStatement pstmt1=null;
//		PreparedStatement pstmt2=null;
//
//		try {
//			System.out.println("now");
//			pstmt = dbConnection.prepareStatement(truncontract);
//			pstmt1=dbConnection.prepareStatement(trunparttime);
//			pstmt2=dbConnection.prepareStatement(trunpermanent);
//			pstmt.execute();
//			pstmt1.execute();
//			pstmt2.execute();
//							
//		}catch (SQLException e1) {
//			e1.printStackTrace();
//			
//		}
//		finally {
//			try {
//				pstmt.close();
//				pstmt1.close();
//				pstmt2.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void truncate()  {
//		try {
//			PreparedStatement pstmt=dbConnection.prepareStatement(trun);
//			pstmt.execute();
//			pstmt.close();
//		}		
//		catch (Exception e) {
//			System.out.println(e);
//		}
//	}
	
	
	public List<Employee> displayJason()  {
		Connection dbConnection = getConnector(mysql,name, pass);

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Employee> list = new ArrayList<Employee>();
		try {
			
		    pstmt=dbConnection.prepareStatement(jasonQuery);
			rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	
	        	Employee e = new Employee(rs.getInt("employeeId"),rs.getString("employeeName"),rs.getInt("salary"),rs.getString("employeeType"));
	        	list.add(e);        	
	        }
	        return list;
		}catch(Exception ex) {
			System.out.println(ex);
		}
		finally {
			try {
				rs.close();
			 	pstmt.close();
				dbConnection.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
  	public List<Employee> displayEmp(int id)  {
		Connection dbConnection = getConnector(mysql,name, pass);

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Employee> list = new ArrayList<Employee>();
		try {
			String query="Select * from new_table where employeeId=?";
		    pstmt=dbConnection.prepareStatement(query);
		    pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	
	        	Employee e = new Employee(rs.getInt("employeeId"),rs.getString("employeeName"),rs.getInt("salary"),rs.getString("employeeType"));
	        	list.add(e);        	
	        }
	        return list;
		}catch(Exception ex) {
			System.out.println(ex);
		}
		finally {
			try {
				rs.close();
			 	pstmt.close();
				dbConnection.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	
	public boolean present(int id) {
		Connection dbConnection = getConnector(mysql,name, pass);
		String check="Select * from new_table where employeeId=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=dbConnection.prepareStatement(check);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
//				ps.close();
				rs.close();
				dbConnection.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	
	
	public Hashtable<Integer,Employee> displayJasonHash()  {
		Connection dbConnection = getConnector(mysql,name, pass);

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Hashtable<Integer, Employee> hashTable=new Hashtable<Integer, Employee>();
//		List<Employee> list = new ArrayList<Employee>();
		try {
			
		    pstmt=dbConnection.prepareStatement(jasonQuery);
			rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	
	        	Employee e = new Employee(rs.getInt("employeeId"),rs.getString("employeeName"),rs.getInt("salary"),rs.getString("employeeType"));
	        	hashTable.put(rs.getInt("employeeId"),e);        	
	        }
	        return hashTable;
		}catch(Exception ex) {
			System.out.println(ex);
		}
		finally {
			try {
				rs.close();
			 	pstmt.close();
				dbConnection.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return hashTable;
	}
	
	public Hashtable<Integer,Employee> displayJason1(int id)  {
		Connection dbConnection = getConnector(mysql,name, pass);

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Hashtable<Integer, Employee> hashTable=new Hashtable<Integer, Employee>();
//		List<Employee> list = new ArrayList<Employee>();
		try {
			String query="Select * from new_table where employeeId=?";
		    pstmt=dbConnection.prepareStatement(query);
		    pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

	        while(rs.next()) {	        	
	        	Employee e = new Employee(rs.getInt("employeeId"),rs.getString("employeeName"),rs.getInt("salary"),rs.getString("employeeType"));
	        	hashTable.put(rs.getInt("employeeId"),e);        	
	        }
	        return hashTable;
		}catch(Exception ex) {
			System.out.println(ex);
		}
		finally {
			try {
				rs.close();
			 	pstmt.close();
				dbConnection.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return hashTable;
	}
	

	public List<Employee> empFilter(Object object) {
		Connection dbConnection = getConnector(mysql,name, pass);

		String query="select * from new_table where employeeType=?";
		List<Employee> list=new ArrayList<Employee>();

		try {
			PreparedStatement ps=dbConnection.prepareStatement(query);
			ps.setString(1, (String) object);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
	        	Employee e = new Employee(rs.getInt("employeeId"),rs.getString("employeeName"),rs.getInt("salary"),rs.getString("employeeType"));
	        	list.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;
		
	}
	
	
	
//	public void dbClose() {
//		try {
//			dbConnection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}







//public void updateDetails(Employee e)  {
//ResultSet resultSet = null;
//addToSubTables();
//try {
//	PreparedStatement ps = dbConnection.prepareStatement(queryCheck);
//	ps.setInt(1, e.getEmployeeId());
//	resultSet = ps.executeQuery();
//	if(resultSet.next()) {
//		System.out.println("Update query");
//		PreparedStatement pstmt = dbConnection.prepareStatement(updateQuery);
//		try {
//			 pstmt.setString(1, e.getEmployeeName());
//			 pstmt.setInt(2, e.getSalary());
//			 pstmt.setString(3,e.getEmployeeType());
//			 pstmt.setInt(4, e.getEmployeeId());
//
//			pstmt.execute();
//			fh = new FileHandler("D:\\logging.txt");  
//	        log.addHandler(fh);
//	        SimpleFormatter formatter = new SimpleFormatter();  
//	        fh.setFormatter(formatter);
//			System.out.println("Details are added");					
//			log.info("Details of "+e.getEmployeeId()+" are updated in database");
//		} 
//		catch (Exception e2) {
//			System.out.println(e2);
//		}
//		finally {
//			pstmt.close();
//			ps.close();
//			resultSet.close();
//		}			  
//	}
//	else {
//        log.addHandler(fh);
//        SimpleFormatter formatter = new SimpleFormatter();  
//        fh.setFormatter(formatter);
//
//		log.info("The id "+e.getEmployeeId()+" you have entered doesnot Exist to update");
//	}
//} catch (SQLException e1) {
//	System.out.println(e1);
//}										   
//}	
//
//public void deleteDetails(int id)  {
//try {
//	PreparedStatement ps = dbConnection.prepareStatement(queryCheck);
//	PreparedStatement pstmt = dbConnection.prepareStatement(deleteQuery);
//	ps.setInt(1, id);
//	ResultSet resultSet = ps.executeQuery();
//	try {
//		
//		if(resultSet.next()) {
//			String s=resultSet.getString("employeeType");
//			if(s.equals("parttime")) {
//				String ptQuery="DELETE from parttime where employeeId=?";
//				PreparedStatement part=dbConnection.prepareStatement(ptQuery);
//				ps.setInt(1, id);
//				part.execute();
//			}
//			else if(s.equals("contract")) {
//				String ptQuery="DELETE from contract where employeeId=?";
//				PreparedStatement part=dbConnection.prepareStatement(ptQuery);
//				ps.setInt(1, id);
//				part.execute();
//			}
//			else  {
//				String ptQuery="DELETE from permenant where employeeId=?";
//				PreparedStatement part=dbConnection.prepareStatement(ptQuery);
//				ps.setInt(1, id);
//				part.execute();
//			}					
//			pstmt.setInt(1, id);
//			pstmt.execute();	
//			log.info("The id "+id+"  in Database are delete");
//		}
//		else {
//			log.info("The id "+id+" doesnot exists in Database to delete");
//		}				
//	}finally {
//		ps.close();
//		resultSet.close();
//		pstmt.close();
//	}
//
//}
// catch (Exception e) {
//		System.out.println(e);
//}							
//}
//









