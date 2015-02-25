/*##########################################################################################
# FILE NAME        : modelThanksGiving.java                                                #
# FILE DESCRIPTION : performs all the database querying to handle ThanksGiving application #
# CREATED BY       : Team ThanksGiving                                                     #
# LAST UPDATED     : November 07, 2014                                                     #
# COURSE NAME      : P565 Software Engineering                                             #
# ORGANIZATION     : Indiana University,Bloomington                                        #
############################################################################################*/


package thanksGiving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class modelThanksGiving {
	
	/*************************************************************************************
	* ClassName : DisplayDetails                                                         *
	* Methods   : 																		 *
	* 			  fetchUserGroupDetails() - queries group details in which the user is   
	 * @throws SQLException 
	 * @throws ClassNotFoundException *
	*************************************************************************************/
		
	public ResultSet fetchTaskByStatus(int groupID, String status) throws ClassNotFoundException, SQLException, FileNotFoundException{
		
		boolean flag = true;
		
		if (status.equals("ALL")){
			flag = false;
		}
		String sqlWithoutStatus = "Select * from thanksgiving.task_details where group_id = ?";
		String sqlWithStatus = "Select * from thanksgiving.task_details where group_id = ? and task_status = ?";
		ResultSet rs = null;
		
		try
	       {		    	  
	    	   	AccessDatabase ad = new AccessDatabase();	
	    	   	Connection conn = ad.getDBConnection();
	    	   	
	    	   	if(flag){
	    	   		PreparedStatement ps = conn.prepareStatement(sqlWithStatus);
		    	   	ps.setInt(1,groupID);
		        	ps.setString(2,status);
		    	   	rs = ps.executeQuery();	    	   		
	    	   	}
	    	   	else{
	    	   		PreparedStatement ps = conn.prepareStatement(sqlWithoutStatus);
		    	   	ps.setInt(1,groupID);
		    	   	rs = ps.executeQuery();	 
	    	   	}   
	    	}
		catch(Exception e)
	       {
	    	   	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
	   			PrintStream prints = new PrintStream(fos);
	   			e.printStackTrace(prints);
	       }
		return rs;
	}
	
public ResultSet fetchWeeklyPoints(int groupID, String email, String status) throws ClassNotFoundException, SQLException, FileNotFoundException{
		
		String sqlWeeklyPoints = "SELECT sum(task_points) as total from thanksgiving.task_details where task_id in (select task_id from thanksgiving.task_details where group_id = ? and assigned_to = ? and Task_status = ? and Task_Completion_Date > (SELECT adddate(curdate(), INTERVAL 1-DAYOFWEEK(curdate()) DAY) WeekStart) and task_completion_date < (SELECT adddate(curdate(), INTERVAL 7-DAYOFWEEK(curdate()) DAY) WeekEnd))";
		ResultSet rs = null;
		
		try
	       {		    	  
	    	   	AccessDatabase ad = new AccessDatabase();	
	    	   	Connection conn = ad.getDBConnection();
	    	   	PreparedStatement ps = conn.prepareStatement(sqlWeeklyPoints);
	    	   	ps.setInt(1,groupID);
	    	   	ps.setString(2,email);
	    	   	ps.setString(3,status);
	    	   	rs = ps.executeQuery();
	    	}
		catch(Exception e)
	       {
	    	   	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
	   			PrintStream prints = new PrintStream(fos);
	   			e.printStackTrace(prints);
	       }
		return rs;
	}
	
public ResultSet fetchParentTask(int groupID) throws ClassNotFoundException, SQLException, FileNotFoundException{
	
	String sqlTasks = "Select * from thanksgiving.task_details_parent where group_id = ?";
	ResultSet rs = null;
	
	try
       {		    	  
    	   	AccessDatabase ad = new AccessDatabase();	
    	   	Connection conn = ad.getDBConnection();
    	   	PreparedStatement ps = conn.prepareStatement(sqlTasks);
    	   	ps.setInt(1,groupID);
    	   	rs = ps.executeQuery();
    	}
	catch(Exception e)
       {
    	   	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
   			PrintStream prints = new PrintStream(fos);
   			e.printStackTrace(prints);
       }
	return rs;
}	
	
	
	
	
	
	
	   class DisplayDetails
	   {

		   UserBean fetchUserGroupDetails(UserBean bean) throws FileNotFoundException
		     {
		       String sql;
		       Connection conn= null;
			   PreparedStatement ps = null;
			   ResultSet rs = null;			  
		       
		       sql = "select group_name,group_id from thanksgiving.group_details where group_id in (select group_id from thanksgiving.member_details where email_id = trim('" + bean.getEmailId() + "'))";
	
		       try
		       {		    	  
		    	   	AccessDatabase ad = new AccessDatabase();	
		    	   	conn = ad.getDBConnection();
			   		ps = conn.prepareStatement(sql);
			   		rs = ps.executeQuery();
			   		
			   		while(rs.next()){
			   			bean.setGroupName(rs.getString("group_name"));
			   			bean.setGroupId(rs.getInt("group_id"));
				        String sql1 = "Select * from thanksgiving.task_details where group_id = ?";
				        PreparedStatement ps1 = conn.prepareStatement(sql1);
				        ps1.setInt(1,rs.getInt("group_id"));
				        ResultSet rs1 = ps1.executeQuery(); 
				        bean.setTaskDetails(rs1);
				    				     
				        String sql2 = "select firstname,lastname from thanksgiving.login_details where email_id in (select email_id from thanksgiving.member_details where group_id = ?)";
				        PreparedStatement ps2 = conn.prepareStatement(sql2);
				        ps2.setInt(1, rs.getInt("group_id"));
				        ResultSet rs2 = ps2.executeQuery();
				        String members="";
				        
				        while(rs2.next()){
				        	members = members + rs2.getString("firstname") + " " + rs2.getString("lastname") +";";				    	 
				        }
				        
				        bean.setGroupMembers(members);
			   		}rs.close();		         	        
		       }
		       catch(Exception e)
		       {
		    	   	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
		   			PrintStream prints = new PrintStream(fos);
		   			e.printStackTrace(prints);
		       }
		        
			return bean;    
		     }    
		   
		   int fetchParentTaskID(int groupId, String task) throws FileNotFoundException{
			   	
			   	String sql;
	    	 	Connection conn= null;
	    	 	PreparedStatement ps = null;
	    	 	ResultSet rs = null;
	    	 	
	    	 	int parentTaskID = 0;
	    	 	    	 	
	    	 	sql = "select Task_Parent_ID from thanksgiving.Task_Details_Parent where Task_Description = ? and Group_ID = ? ";
	    	 	try
	    	 	{
	    	 		AccessDatabase ad = new AccessDatabase();	
			    	conn = ad.getDBConnection();
	    			ps = conn.prepareStatement(sql);
	    			ps.setString(1, task);
	    			ps.setInt(2, groupId);
	    		    rs = ps.executeQuery();
	    		    
	    		    while(rs.next())
	    		    {
	    		    	parentTaskID = rs.getInt("Task_Parent_ID");
	    		    }
	    	 	}
	    	 	catch(Exception e)
	    		{
	    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
		   			PrintStream prints = new PrintStream(fos);
		   			e.printStackTrace(prints);
	    		}
	    	 	return parentTaskID;
		   }
	   }

	   /*************************************************************************************
		* ClassName : ModifyDetails                                                         *
		* Methods   : 																		*
		* 			  assignTaskTo() - assigns a given task to an individual user           *
		* 			  updateTaskRecurDetails() - makes a recurring task entry 			    *
		* 			  updateTaskStatus() - updates task status to completed					*											*
		*************************************************************************************/
	   class ModifyDetails
	   {
	     
		   void autoAdjustTaskPoints(String task, int groupID) throws FileNotFoundException
		     {		    	 
			    	 	String unassignedTasks, totalPoints, taskPoints;
			    	 	Connection conn= null;
			    	 	PreparedStatement ps = null;
			    	 	PreparedStatement ps1 = null;
			    	 	PreparedStatement ps2 = null;
			    	 	ResultSet adjustPoints = null; 
			    	 	ResultSet sumPoints = null;
			    	 	ResultSet splitPoints = null;
			    	 	int taskCount = 0;
			    	 	double newPoint = 0;
			    	 	
			    	 	taskPoints = "select task_points from thanksgiving.Task_Details where group_id = ? and task_description = ?";
			    	 	totalPoints = "Select count(task_description) as counter from thanksgiving.task_details where group_id = ? and assigned_to = ?";
			    	 	unassignedTasks = "Select * from thanksgiving.Task_Details_Parent where group_id = ? and task_parent_id in (select parent_Task_ID from thanksgiving.task_details where group_id = ? and assigned_to = ?)";
			    		
			    		try
			    		{
			    			AccessDatabase ad = new AccessDatabase();	
					    	conn = ad.getDBConnection();
			    			ps = conn.prepareStatement(unassignedTasks);
			    			ps.setInt(1, groupID);
			    			ps.setInt(2, groupID);
			    		    ps.setString(3, "UNASSIGNED");
			    		    adjustPoints = ps.executeQuery();
			    		    
			    		    ps1 = conn.prepareStatement(totalPoints);
			    		    ps1.setInt(1, groupID);
			    		    ps1.setString(2, "UNASSIGNED");
			    		    sumPoints = ps1.executeQuery();
			    		    
			    		    ps2 = conn.prepareStatement(taskPoints);
			    		    ps2.setInt(1, groupID);
			    		    ps2.setString(2, task);
			    		    splitPoints = ps2.executeQuery();
			    		    
			    		    while (sumPoints.next())
			    		    {
			    		    	//pointsTotal = sumPoints.getInt("points");
			    		    	taskCount = sumPoints.getInt("counter");
			    		    }
			    		    
			    		    while (splitPoints.next()){
			    		    	newPoint = splitPoints.getInt("Task_Points");
			    		    	newPoint = newPoint * (0.25);
			    		    	if (taskCount > 1){
			    		    	newPoint = newPoint / (taskCount - 1) ;
			    		    	}
			    		    }
			    		    if(taskCount > 1){
			    		    while (adjustPoints.next()){
			    		    	if((adjustPoints.getString("Task_Description")).equals(task)){
			    		    		double point = adjustPoints.getInt("Task_Points"); 
			    		    		point = point - (point * (0.25));
			    		    		updateParentTaskPoints(task, point, groupID);
			    		    	}
			    		    	else{
			    		    		double point = adjustPoints.getInt("Task_Points");
			    		    		point = point + newPoint;
			    		    		updateParentTaskPoints(adjustPoints.getString("Task_Description"), point, groupID);
			    		    		updateTaskPoints(adjustPoints.getString("Task_Description"), point, groupID);
			    		    	}
			    		    }}

			    		}
			    		catch(Exception e)
			    		{
			    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
				   			PrintStream prints = new PrintStream(fos);
				   			e.printStackTrace(prints);
			    		}
			     }  
		   
		   void updateParentTaskPoints(String task, double points, int groupID) throws FileNotFoundException
		     {		    	 
			    	 	String sql;
			    	 	Connection conn= null;
			    	 	PreparedStatement ps = null;
			    	 				    	 	
			    	 	sql = "Update thanksgiving.Task_Details_Parent set task_points = ? where group_id = ? and task_description  = ?";
			    		
			    		try
			    		{
			    			AccessDatabase ad = new AccessDatabase();	
					    	conn = ad.getDBConnection();
			    			ps = conn.prepareStatement(sql);
			    			ps.setDouble(1, points);
			    			ps.setInt(2, groupID);
			    		    ps.setString(3, task);
			    		    ps.executeUpdate();
			    		}
			    		catch(Exception e)
			    		{
			    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
				   			PrintStream prints = new PrintStream(fos);
				   			e.printStackTrace(prints);
			    		}
			     }  
		     
		   
	     void assignTaskTo(UserBean bean, String task, String assignedTo) throws FileNotFoundException
	     {		    	 
		    	 	String sql;
		    	 	Connection conn= null;
		    	 	PreparedStatement ps = null;
		    	
		    	 	String[] assignedToName = assignedTo.split(" ");
		    	 	
		    	 	autoAdjustTaskPoints(task, bean.getGroupId());

		    		sql = "update thanksgiving.TASK_DETAILS set assigned_to = (select email_id from thanksgiving.LOGIN_DETAILS where firstname = ? and lastname= ?), assigned_by = ? where task_description = ? and group_id = ?";
		    		
		    		try
		    		{
		    			AccessDatabase ad = new AccessDatabase();	
				    	conn = ad.getDBConnection();
		    			ps = conn.prepareStatement(sql);
		    			ps.setString(1, assignedToName[0]);
		    			ps.setString(2, assignedToName[1]);
		    		    ps.setString(3, bean.getEmailId());
		    		    ps.setString(4, task);
		    		    ps.setInt(5, bean.getGroupId());
		    		    ps.executeUpdate();

		    		}
		    		catch(Exception e)
		    		{
		    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
			   			PrintStream prints = new PrintStream(fos);
			   			e.printStackTrace(prints);
		    		}
		     }
	     
	     	void updateTaskStatus(UserBean bean, int taskId) throws FileNotFoundException
	     	{
	     		String sql;
	    	 	Connection conn= null;
	    	 	PreparedStatement ps = null;
	 	    
	    	 	sql = "update thanksgiving.TASK_DETAILS set Task_Status = \"Complete\" where Task_ID = ?";
	    	 		 	    
	 	    try
	 	    {
	 	    	AccessDatabase ad = new AccessDatabase();	
		    	conn = ad.getDBConnection();
	 	    	ps = conn.prepareStatement(sql);
	 	    	ps.setInt(1, taskId);
	 	    	ps.executeUpdate();
	 	    	EnterDetails ed = new EnterDetails();
	 	    	ed.createRecurringTask(bean,taskId);
	 	    	
	 	    }
	 	    catch(Exception e)
	 	    {
	 	    	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
	   			PrintStream prints = new PrintStream(fos);
	   			e.printStackTrace(prints);
	 	    }
	     	}	
	     	
	     	void updateTaskPoints(String task, double points, int groupID) throws FileNotFoundException
	     	{
	     		String sql;
	    	 	Connection conn= null;
	    	 	PreparedStatement ps = null;
	 	    
	    	 	sql = "update thanksgiving.TASK_DETAILS set Task_Points = ? where Task_Description = ? and Group_ID = ? and Assigned_to = ?";
	    	 		 	    
	 	    try
	 	    {
	 	    	AccessDatabase ad = new AccessDatabase();	
		    	conn = ad.getDBConnection();
	 	    	ps = conn.prepareStatement(sql);
	 	    	ps.setDouble(1, points);
	 	    	ps.setString(2, task);
	 	    	ps.setInt(3, groupID);
	 	    	ps.setString(4, "UNASSIGNED");
	 	    	ps.executeUpdate();
	 	    		 	    	
	 	    }
	 	    catch(Exception e)
	 	    {
	 	    	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
	   			PrintStream prints = new PrintStream(fos);
	   			e.printStackTrace(prints);
	 	    }
	     	}	

	   }
	   

	   /*************************************************************************************
		* ClassName : EnterDetails                                                          *
		* Methods   : 																		*
		* 			  createGroup() - creates a new group with members                      *
		* 			  createTask() - creates a new task in the given group     			    *
		* 			  createUser() - creates a new user for the thanksgiving application    *											*
		*************************************************************************************/
	   class EnterDetails
	   {
		   UserBean createUser(UserBean bean) throws FileNotFoundException
		     {
		    	 	String sql,sql1;
		    	 	Connection conn= null;
		    	 	PreparedStatement ps,ps1 = null;
		    	 	ResultSet rs = null;
		    
		    	 	sql = "select email_id from thanksgiving.LOGIN_DETAILS where email_id = ?";

			       try
			       {
			    	   	AccessDatabase ad = new AccessDatabase();	
			    	   	conn = ad.getDBConnection();
			    	   	ps = conn.prepareStatement(sql);
				   		ps.setString(1, bean.getEmailId());
			   		    rs = ps.executeQuery();
			   		   
			   		    if(rs.next()){
			   		       	bean.setEmailId(null);
			   		    	bean.setPwd(null);
			   		    	bean.setValidUser(false);
			   		    }
			   		    else
			   		    {	
			   		    	sql1 = "insert into thanksgiving.LOGIN_DETAILS values (?, ?, ?, ?)"	;
			   		    
			   		    	ps1= conn.prepareStatement(sql1);
			   		    	ps1.setString(1, bean.getfName());
			   		    	ps1.setString(2, bean.getlName());
			   		    	ps1.setString(3, bean.getEmailId());
			   		    	ps1.setString(4, bean.getPwd());
			   		    	ps1.executeUpdate();
			   		    	bean.setValidUser(true);
			   		    }
			   		}
				   
			       catch(Exception e)
				   	{
			    	   	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
			   			PrintStream prints = new PrintStream(fos);
			   			e.printStackTrace(prints);
				   	}  		       
				return bean;    
			     }

		   
	     UserBean createGroup(UserBean bean) throws FileNotFoundException
	     {
	    	 	String sql;
	    	 	Connection conn= null;
	    	 	PreparedStatement ps = null;
	    	 	ResultSet rs = null;
	    	 	int GID = 1;  //Set default value
	    	 	//Random randomGenerator = new Random();
			  			   
			   	String group = bean.getGroupName();
		   		String membersList = bean.getGroupMembers();
		   		String[] members = membersList.split(",");

		   		sql = "insert into thanksgiving.GROUP_DETAILS (group_name) values (?)";

		       try{
		    	   	AccessDatabase ad = new AccessDatabase();	
		    	   	conn = ad.getDBConnection();
		    	    ps = conn.prepareStatement(sql);
			   		//ps.setString(1, GID);
		   		    ps.setString(1, group);
		   		    ps.executeUpdate();
		   		}
			   	catch(Exception e){
			   		FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
		   			PrintStream prints = new PrintStream(fos);
		   			e.printStackTrace(prints);
			   	}  
			   
		       sql = "select group_id from thanksgiving.GROUP_DETAILS  where group_name = ?";

		       try{
		    	   	AccessDatabase ad = new AccessDatabase();	
		    	   	conn = ad.getDBConnection();
		    	    ps = conn.prepareStatement(sql);
			   		ps.setString(1, group);
		   		    rs = ps.executeQuery();
		   		    
		   		    while (rs.next()){
			    	   GID = rs.getInt("group_id");
				   }
		   		}
			   	catch(Exception e){
			   		FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
		   			PrintStream prints = new PrintStream(fos);
		   			e.printStackTrace(prints);
			   	} 
		       
		       bean.setGroupId(GID);
		       
		       
		       for(int i=0; i < members.length; i++){
			   			membersList = members[i];
			   			sql = "insert into thanksgiving.MEMBER_DETAILS values ( ?, ?)";
			   			
			   		try
			   		{
			   			AccessDatabase ad = new AccessDatabase();	
				    	conn = ad.getDBConnection();
			   			ps = conn.prepareStatement(sql);
			   			ps.setString(1, membersList);
			   		    ps.setInt(2, GID);
			   		    ps.executeUpdate();

			   		}
			   		catch(Exception e)
			   		{
			   			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
			   			PrintStream prints = new PrintStream(fos);
			   			e.printStackTrace(prints);
			   		} 
			   		}
			return bean;    
		     }
	     
	     void createTask(UserBean bean, String task, int points, String recurring, String taskDate) throws FileNotFoundException
	     {		
	    	 	String sql, fetchParentPoints;
	    	 	Connection conn= null;
	    	 	PreparedStatement ps = null;
	    	 	String parentInsert;
		    	PreparedStatement parentPS = null;
		    	ResultSet rs = null;
		    	int newPoints = points; 
	    	 		    	 	
	    	 	DisplayDetails dd = new DisplayDetails();
	    	 	int parentTaskID = dd.fetchParentTaskID(bean.getGroupId(), task);
	    	 	
	    	 	if(parentTaskID == 0){
	    	 		try
		    	 	{
	    	 			AccessDatabase ad = new AccessDatabase();	
		    	 	   	conn = ad.getDBConnection();
		    			parentInsert = "Insert into thanksgiving.task_details_parent (Task_Description, Task_Points, Group_ID) values(?, ?, ?)";
	    		    	parentPS = conn.prepareStatement(parentInsert);
	    		    	parentPS.setString(1, task);
	    		    	parentPS.setInt(2, points);
	    		    	parentPS.setInt(3, bean.getGroupId());
	    		    	parentPS.executeUpdate();
	    		    	
	    		    	parentTaskID = dd.fetchParentTaskID(bean.getGroupId(), task);
		    	 	}
	    	 		catch(Exception e)
		    		{
		    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
			   			PrintStream prints = new PrintStream(fos);
			   			e.printStackTrace(prints);
		    		}
	    	 	}
	    	 	else{
	    	 		try
		    	 	{
	    	 			AccessDatabase ad = new AccessDatabase();	
		    	 	   	conn = ad.getDBConnection();
		    			fetchParentPoints = "Select task_points from thanksgiving.task_details_parent where task_parent_id = ?";
	    		    	parentPS = conn.prepareStatement(fetchParentPoints);
	    		    	parentPS.setInt(1, parentTaskID);
	    		    	rs = parentPS.executeQuery();
	    		    	while(rs.next()){
	    		    		newPoints = rs.getInt("task_points");
	    		    	}
		    	 	}
	    	 		catch(Exception e)
		    		{
		    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
			   			PrintStream prints = new PrintStream(fos);
			   			e.printStackTrace(prints);
		    		}
	    	 	}
	    	 		
	    	 	
	    	 	String[] tempDateForTask = taskDate.split("-");
	    	 	String dateForTask = tempDateForTask[2] + "-" + tempDateForTask[1] + "-" +  tempDateForTask[0];
	    	 	
	    	 	sql = "insert into thanksgiving.TASK_DETAILS (task_description, task_status, task_points, assigned_to, assigned_by, group_ID, recurrent, task_completion_date, parent_task_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    		
	    		try
	    		{
	    			AccessDatabase ad = new AccessDatabase();	
			    	conn = ad.getDBConnection();
	    			ps = conn.prepareStatement(sql);
	    			//ps.setInt(1, TID);
	    		    ps.setString(1, task);
	    		    ps.setString(2, "Pending");
	    		    ps.setInt(3, newPoints);
	    		    ps.setString(4, "UNASSIGNED");
	    		    ps.setString(5, "UNASSIGNED");
	    		    ps.setInt(6, bean.getGroupId());
	    		    ps.setString(7, recurring);
	    		    ps.setString(8, dateForTask);
	    		    ps.setInt(9, parentTaskID);
	    		    ps.executeUpdate();

	    		}
	    		catch(Exception e)
	    		{
	    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
		   			PrintStream prints = new PrintStream(fos);
		   			e.printStackTrace(prints);
	    		}  
	     }
	     
	     void createRecurringTask(UserBean bean, int taskId) throws FileNotFoundException{
	    	 	String sql;
	    	 	Connection conn= null;
	    	 	PreparedStatement ps = null;
	    	 	ResultSet rs = null;
	    	 		    	 	
	    		sql = "select * from thanksgiving.TASK_DETAILS where task_id = ?";
	    		
	    		try
	    		{	
	    			AccessDatabase ad = new AccessDatabase();	
			    	conn = ad.getDBConnection();
	    			ps = conn.prepareStatement(sql);
	    			ps.setInt(1, taskId);
	    		    rs = ps.executeQuery();
	    		    
	    		    int parentTaskID = 0;
	    		    
	    		    while(rs.next()){
	    		    	
	    		    	String recur = rs.getString("Recurrent");
	    	    		if (!recur.equalsIgnoreCase("no")){
	    	    			
	    	    			parentTaskID = rs.getInt("Parent_Task_ID");
	    	    			Date taskDate = rs.getDate("Task_Completion_Date");
	    	    			Date newDate = new Date();
	    	    			Calendar cal = Calendar.getInstance();
	    	    			
	    	    			if (recur.equalsIgnoreCase("daily")){
	    	    				cal.setTime(taskDate);
	    	    		        cal.add(Calendar.DATE, 1); 
	    	    		        newDate=cal.getTime();
	    	    			}
	    	    			else if (recur.equalsIgnoreCase("weekly")){
	    	    				cal.setTime(taskDate);
	    	    		        cal.add(Calendar.DATE, 7); 
	    	    		        newDate=cal.getTime();
	    	    			}
	    	    			else if (recur.equalsIgnoreCase("monthly")){
	    	    				cal.setTime(taskDate);
	    	    		        cal.add(Calendar.MONTH, 1); 
	    	    		        newDate=cal.getTime();
	    	    			}
	    	    			
	    	    			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    	    			String convDate = sdf.format(newDate);
	    	    			
	    	    			PreparedStatement ps1 = null;
	    	    			ResultSet rs1 = null;
	    	    			String updatedPoints = "Select Task_Points from thanksgiving.task_details_parent where task_parent_ID = ?";
	    	    			ps1 = conn.prepareStatement(updatedPoints);
	    	    			ps1.setInt(1, parentTaskID);
	    	    		    rs1 = ps1.executeQuery();
	    	    			int newPoint = rs.getInt("task_points");
	    	    			while (rs1.next()){
	    	    				newPoint = rs1.getInt("task_points");
	    	    			}
	    	    			    	    			
	    	    			createTask(bean,rs.getString("task_description"), newPoint,  rs.getString("recurrent"), convDate);
	    	    		}
	    		    }  		    

	    		}
	    		catch(Exception e)
	    		{
	    			FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
		   			PrintStream prints = new PrintStream(fos);
		   			e.printStackTrace(prints);
	    		}  
	     }
	     
	     }
	   
	   	
	   /*************************************************************************************
		* ClassName : AccessDatabase                                                        *
		* Methods   : 																		*
		* 			  authenticateUser() - authenticates individual user			        *
		* 			  getDBConnection() - returns the connection string for thanksgiving DB *
		*************************************************************************************/
	   
	   class AccessDatabase
	   {
		   Connection getDBConnection() throws ClassNotFoundException, SQLException
			 {
				   String driverName = "com.mysql.jdbc.Driver";
				   String url = "jdbc:mysql://localhost:3306/";
				   String user = "root";
				   String pwd = "Shavi19101988";
				   Connection con = null;
				   Class.forName(driverName);
				   con = DriverManager.getConnection(url, user, pwd);
				   return con;
			 }
			
		 	 
		 UserBean authenticateUser(UserBean bean) throws FileNotFoundException
	     {
	       String sql;
	       Connection conn= null;
		   PreparedStatement ps = null;
		   ResultSet rs = null;
		    
	       sql = " Select * FROM thanksgiving.LOGIN_DETAILS where Email_ID = ?";

	       try
	       {
	    	    conn = getDBConnection();
		   		ps = conn.prepareStatement(sql);
		   		ps.setString(1,bean.getEmailId());
		   		rs = ps.executeQuery();
		   		
		   		bean.setValidUser(false);
	         
	         while(rs.next()){
	           
	           if ((bean.getPwd()).equals(rs.getString("Password")))
	           {
	        	   bean.setfName(rs.getString("FirstName"));
	        	   bean.setlName(rs.getString("LastName"));
	        	   bean.setValidUser(true);
	             
	           }
	           else
	           {
	             System.out.println("!!! USER AUTHENTICATION FAILED !!!");
	           }
	         }
	         rs.close();
	         	        
	       }
	       catch(Exception e)
	       {
	    	   	FileOutputStream fos = new FileOutputStream(new File("C:/Users/ShravanJagadish/Desktop/Thanksgiving/log.txt"));
	   			PrintStream prints = new PrintStream(fos);
	   			e.printStackTrace(prints);	    	   
	       }
		return bean;    
	     }

	   }

	 
}
