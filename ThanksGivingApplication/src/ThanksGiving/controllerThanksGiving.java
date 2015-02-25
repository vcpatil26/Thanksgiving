/*##########################################################################################
# FILE NAME        : controllerThanksGiving.java                                           #
# FILE DESCRIPTION : handlesl the controller passing activity for ThanksGiving application #
# CREATED BY       : Team ThanksGiving                                                     #
# LAST UPDATED     : November 07, 2014                                                     #
# COURSE NAME      : P565 Software Engineering                                             #
# ORGANIZATION     : Indiana University,Bloomington                                        #
############################################################################################*/

package thanksGiving;

import java.io.IOException;

//import javax.swing.JOptionPane;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thanksGiving.modelThanksGiving.AccessDatabase;
import thanksGiving.modelThanksGiving.DisplayDetails;
import thanksGiving.modelThanksGiving.EnterDetails;
import thanksGiving.modelThanksGiving.ModifyDetails;

/**
 * Servlet implementation class controllerThanksGiving
 */
@WebServlet("/controllerThanksGiving")
public class controllerThanksGiving extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controllerThanksGiving() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/LoginForm.jsp").forward(request, response);
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Create objects for all model classes to be used later
		modelThanksGiving tg = new modelThanksGiving();
		AccessDatabase adb = tg.new AccessDatabase();
		EnterDetails ed = tg.new EnterDetails();
		DisplayDetails dd = tg.new DisplayDetails();
		ModifyDetails md = tg.new ModifyDetails();
		
		//check the calledBy parameter to identify which page calls the program
		String calledBy = "FirstCall";
		if(request.getParameter("calledBy") != null){
			calledBy = request.getParameter("calledBy");
		}
		
		//If user is logging in, then authenticate
		if (calledBy.equals("FirstCall")){	
			UserBean user = new UserBean();
		
			user.setEmailId(request.getParameter("email_id"));
			user.setPwd(request.getParameter("password"));
			user = adb.authenticateUser(user);
		
			if(user.isValidUser()){
				user = dd.fetchUserGroupDetails(user);
				HttpSession session = request.getSession(true);
				session.setAttribute("currentUser", user);
				getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);
			}
			else{
				//-- not working -- JOptionPane.showMessageDialog(null, "Invalid credentials, please try again");
				getServletContext().getRequestDispatcher("/LoginForm.jsp").forward(request, response);
			}
		}
		
		//if creating user, then verify if user already exists, otherwise create user
		if(calledBy.equals("CreateUser")){
		
			UserBean user = new UserBean();
			
			user.setfName(request.getParameter("fname"));
			user.setlName(request.getParameter("lname"));
			user.setEmailId(request.getParameter("useremail"));
			user.setPwd(request.getParameter("userpassword"));
			
			user = ed.createUser(user);
			
			if(user.isValidUser()){
				HttpSession session = request.getSession(true);
				session.setAttribute("currentUser", user);
				getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);				
			}
			else{
				//-- Not working --JOptionPane.showMessageDialog(null, "The EmailID provided already exists. please try again with a different EmailId");
				getServletContext().getRequestDispatcher("/UserExists.jsp").forward(request, response);				
			}
		}
		else
		{	
			HttpSession session = request.getSession(true);
			UserBean user = (UserBean)(session.getAttribute("currentUser"));
			switch(calledBy){
									
				case "CreateGroup":
					user.setGroupName(request.getParameter("group"));
					user.setGroupMembers(request.getParameter("member"));
					user = ed.createGroup(user);
					session.setAttribute("currentUser", user);
					getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);
					break;
					
				case "CreateTask":
					String task = request.getParameter("task");
					int points = Integer.parseInt(request.getParameter("points"));
					String recurring  = request.getParameter("recurring");
					String taskDate = request.getParameter("datetimepicker");
		    		ed.createTask(user, task, points, recurring, taskDate);
		    		user = dd.fetchUserGroupDetails(user);
		    		session.setAttribute("currentUser", user);
					getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);
					break;
					
				case "AssignTask":
					String taskAssign = request.getParameter("taskDetail");
					String taskAssignTo = request.getParameter("taskAssignTo");
					//System.out.println(taskAssign + taskAssignTo);
					md.assignTaskTo(user, taskAssign, taskAssignTo);
					//md.updateTaskRecurDetails();
		    		user = dd.fetchUserGroupDetails(user);
		    		session.setAttribute("currentUser", user);
					getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);
					break;
					
				 case "TaskComplete":
	                 int taskId;
	                 String taskIdVal = request.getParameter("completedTaskId");
	 
	                 if (!taskIdVal.equals("")){
	                	 taskId = Integer.parseInt(taskIdVal);
	                     md.updateTaskStatus(user,taskId);
	                 }
	                 //md.updateTaskRecurDetails();
	                 user = dd.fetchUserGroupDetails(user);
	                 session.setAttribute("currentUser", user);
	                 getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);
	                 break;
	                
					
				default:
					break;
			}
		}
	}
	
	protected boolean sessionExists(HttpServletRequest request){
		HttpSession sessionExists = request.getSession(false);
		if(sessionExists == null)
			return true;
		return false;
	}

}
