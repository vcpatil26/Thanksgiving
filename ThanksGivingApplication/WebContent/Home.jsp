<!-- *######################################################################################
# FILE NAME        : Home.jsp    			                                               #
# FILE DESCRIPTION : home page with menu bars to use the  ThanksGiving application         #
# CREATED BY       : Team ThanksGiving                                                     #
# LAST UPDATED     : November 07, 2014                                                     #
# COURSE NAME      : P565 Software Engineering                                             #
# ORGANIZATION     : Indiana University,Bloomington                                        #
#######################################################################################* -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ page import = "thanksGiving.UserBean" %>
    <%@ page import = "thanksGiving.modelThanksGiving" %>
    <%@ page import = "java.sql.*" %>
    <%@ page import = "java.util.Date" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="IMAGES/favicon.png">
    
    <script src="jquery-1.6.2.min.js"></script>
	<script src="jquery-ui-1.8.15.custom.min.js"></script>
	<link rel="stylesheet" href="jquery/jqueryCalendar.css">
	<script language="javascript" type="text/javascript" src="JS/datetimepicker.js">

		//Date Time Picker script- by TengYong Ng of http://www.rainforestnet.com
		//Script featured on JavaScript Kit (http://www.javascriptkit.com)
		//For this script, visit http://www.javascriptkit.com 
	</script>

    <title>Home Page</title>
	
	<!-- Bootstrap core CSS -->
    <link href="CSS/bootstrap.css" rel="stylesheet">
	<link href="CSS/thanksgiving.css" rel="stylesheet">
	<link href="CSS/sidebar.css" rel="stylesheet">
	
	<script type="text/javascript">
	function validateTaskID(){
		var taskID = document.getElementById("completedTaskId").value;
		
		if(taskID != null){
			var isNumber =  /^\d+$/.test(taskID);
			if(!isNumber){
				alert("Please enter a valid Task ID");
				document.getElementById("completedTaskId").value = "";
			}
		}
	}
	
	function updatePoints(){
		var dropDown = document.getElementById("taskAssign");
		var points = document.getElementById("assignPoints");
		var dropDownValue = dropDown.options[dropDown.selectedIndex].value;
		
		if(dropDownValue == 0){
			alert("Please choose a Task");
			points.value = 0;
			}
		else{
				points.value = dropDownValue;
					document.getElementById("taskDetail").value = dropDown.options[dropDown.selectedIndex].text;
			}
		}	
	
	function validateAssignTask(){
		var points = document.getElementById("assignPoints").value;
		var dropDown = document.getElementById("taskAssign");
		var taskAssignTo = document.getElementById("taskAssignTo");
		var taskAssignToValue = taskAssignTo.options[taskAssignTo.selectedIndex].value;
		
		if(points == 0){
			alert("Please choose a task!");
			dropDown.focus();
			return false;
		}
		else if(taskAssignToValue==null || taskAssignToValue == ""){
			alert("No group members in your group, add group members and then assign task.");
			taskAssignTo.focus();
			return false;
		}
		return true;
	} 
	
	function logout(){
		alert("Are you sure you want to logout?");
		window.load("LoginForm.jsp");
	}
	</script>
	</head>
<%

UserBean user = (UserBean)(session.getAttribute("currentUser"));
String name = user.getfName() + " " + user.getlName();
String emailID = user.getEmailId();
%>
  <body>
	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          
          <a class="navbar-brand" href="#">
		  <img class="img-responsive" alt="" src="IMAGES/thanksgiving.jpg">
		  </a>
		  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		<!--     <span class="sr-only">Toggle navigation</span> -->
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
				</button>
		  </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav"></ul>
		 </div>
		 <div class="nav navbar-nav navbar-left">
		 	<p style= "color:white"><b>Welcome <%= name %> </b></p>
		 </div>
		 <div class="nav navbar-nav navbar-right">
            
            &nbsp;
            <a href="DATA/ThanksGiving_AboutUs.pdf" target="_blank"><button type="button" class="btn  btn-success" id="about" >About Us</button></a>
            &nbsp; 
            <button type="button" class="btn  btn-success" id="logout" onclick="logout()">Logout</button>
            <!-- <p style= "color:white"><b>Logout</b></p> -->
         
          </div>
	</div><!--/.nav-collapse -->
		<div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="#">
                     Task Bar
                    </a>
                </li>
                <li>
                   <a class="btn btn-large btn-primary" id="btn" href="#modal-container-createGroup" role="button" data-toggle="modal"><h4>Create a Group</h4></a>
                </li>
                <li>
                    <a class="btn btn-large  btn-primary" id="btn" href="#modal-container-createTask" role="button" data-toggle="modal"><h4>Create Task</h4></a>
                </li>
                <li>
                    <a class="btn btn-large  btn-primary" id="btn" href="#modal-container-assignTask" role="button" data-toggle="modal"><h4>Assign Task</h4></a>
                </li>
                <li>
                    <a class="btn btn-large  btn-primary" id="btn" href="#modal-container-viewTask" role="button" data-toggle="modal"><h4> My Tasks</h4></a>
                </li>
                <li>
                    <a class="btn btn-large  btn-primary" id="btn" href="#modal-container-overdueTask" role="button" data-toggle="modal"><h4>Pending/Overdue Task</h4></a>
                </li>
                <li>
                    <a class="btn btn-large  btn-primary" id="btn" href="#modal-container-oldTasks" role="button" data-toggle="modal"><h4>Old Task Names</h4></a>
                </li>  
            </ul>
        </div>
		</div>
		</div>
        <!-- /#sidebar-wrapper -->
		
		 <div class="modal fade" id="modal-container-createGroup" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" >
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="IMAGES/Close.jpg" height=12 width=12 ></button>
                            <h2 class="modal-title text-center" id="myModalLabel">Create Group</h2>
                        </div>
                        <div class="modal-body">
                            <form id="createGroup" class="form-horizontal well" method="Post" action="controllerThanksGiving">

                                <div class="form-group">
                                    <label for="task" class="col-sm-4 control-label">Group Name</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="group" id="group" placeholder="Enter Group Name" type="text" required="required">
                                    </div>
                                </div>
                                
                                 <div class="form-member">
                                    <label for="task" class="col-sm-4 control-label">Member Email</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="member" id="member" placeholder="Enter member Email ID" type="text" required="required">
                                        <table><tr><td> *Enter group members Email ID separated by a comma [including your Email ID] </td> </tr></table>
                                    </div>
                                </div>
                   					<div class="form-group">
                                    <div class="col-sm-4 pull-right">
                                       <input type="hidden" id="calledBy" name="calledBy" value="CreateGroup">
                                        <button type="submit" class="btn  btn-success" id="CreateGroup_Submit" >Create Group </button>
                                    </div>
                                </div>
								<p id="display"></p>
                            </form>
                            
                        </div>
                    </div>
                </div>
			</div>
			<div class="modal fade" id="modal-container-createTask" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true"><img src="IMAGES/Close.jpg" height=12 width=12 ></button>
                            <h2 class="modal-title text-center" id="myModalLabel1">Create Task</h2>
                        </div>
                        <div class="modal-body">
                               <form id="createTask" class="form-horizontal well" method="Post" action="controllerThanksGiving">
								<div class="form-group">
                                    <label for="task" class="col-sm-4 control-label">Task</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="task" id="task" placeholder="Enter the task"  required="required">
                                    </div>
                                    <div class="form-group">
                                    <label>* To re-use a previous task and continue its point system: </label>
                                    <label>1. Identify Task Name from <i> Old Task Names </i> tab </label>
                                    <label>2. Type the exact name in the 'Task' field </label>
                                    <label>Assign any task point value [old task point value will be used] </label>
                                </div>
                                </div>
								<div class="form-group">
								 <label class="col-sm-4 control-label">Recurring:</label>                                    
                                     <div class="form-inline"> 
                                          <div class="col-sm-8">
                                            <select class="form-control" id="recurring" name="recurring">
                                                <option>No</option>
                                                <option>Daily</option>
                                                <option>Weekly</option>
                                                <option>Monthly</option>
                                            </select> 
                                           </div>
                                    </div>
                                </div> 
                                <div class="form-group">
                                 <label class="col-sm-4 control-label">Complete By:</label>
                                    <div class="col-sm-8">
                                        <div class='input-group date' >
                                        	<input id="datetimepicker" name="datetimepicker" type="text" class="form-control" required="required">
                                        	<a href="javascript:NewCal('datetimepicker','ddmmyyyy')">
                                        	<img src="IMAGES/cal.gif" width="14" height="14" border="0" alt="Pick a date"></a>                                                                                       
                                        </div>
                                    </div>
                                    </div>
                                <div class="form-group">
								 <label class="col-sm-4 control-label">Points:</label>
                                    <div class="col-sm-8">
								<div class="btn-group">
								
						        <input class="form-control" type="text" name="points" id="points" placeholder="Enter points < 100" required="required">
								</div>
                                </div>
								</div>
								
                                <div class="form-group">
                                    <div class="col-sm-8 pull-right">
                                    <input type="hidden" id="calledBy" name="calledBy" value="CreateTask">
                                    <button type="submit" class="btn  btn-success" id="Signup_Submit" onclick="">Create Task </button>
                                    </div>
                                </div>
                                <p id="display1"></p>
                            </form>
                                               	   
                        </div>
                    </div>
                </div>
			</div>
			<div class="modal fade" id="modal-container-assignTask" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="IMAGES/Close.jpg" height=12 width=12></button>
                            <h2 class="modal-title text-center" id="myModalLabel1">Assign Task</h2>
                        </div>
                        <div class="modal-body">
                               <form id="assignTask" class="form-horizontal well" method="Post" action="controllerThanksGiving" onsubmit="return validateAssignTask();">
								
                                <div class="form-group">
                                    <label for="task" class="col-sm-4 control-label">Task</label>  
                                                                        
                                    <% if (null == user.getGroupName()) {
                                    	//do nothing
                                    	} 
                                    	else { %>
                              				<select name="taskAssign" id="taskAssign" required="required" onChange="updatePoints()" >
								   				<option value="0">-- Select a task from list -- </option>
								   					<% 	modelThanksGiving tg = new modelThanksGiving();
                           								ResultSet tasks = tg.fetchTaskByStatus(user.getGroupId(),"ALL");
								   						while(tasks.next()){ 
                           									if((tasks.getString("task_status")).equals("Pending")) {%>
                           										<option value="<%=(tasks).getString("task_points")%>"> <%= (tasks).getString("task_description")%> </option>
                        	   								<%}
                           								}%>  </select>
                           				<%}%>                
                                  </div>  
                                  		  
								  <div class="form-group">
								 	<label class="col-sm-4 control-label">Points:</label>
                                    	<div class="col-sm-8">
											<div class="btn-group">
								       			<input class="form-control" type="text" name="assignPoints" id="assignPoints" value=0 readonly>
											</div>
                                		</div>
								</div>
                                
								<div class="form-group">
                                    <label class="col-sm-4 control-label">Assign to:</label>
                                    <% if (null == user.getGroupName()) {
                                    	//do nothing
                                    } 
                                    else { %>
                              			<select name="taskAssignTo" id="taskAssignTo" required="required" >
								   				<% String[] members = user.getIndividualGroupMembers();
                           						    for(int i=0;i<members.length;i++){%>
                           								<option value="<%=members[i]%>"> <%= members[i]%> </option>
                        	   	  					<%} %> </select>
                           				<%}%>
                                </div> 
								
								
                                <div class="form-group">
                                    <div class="col-sm-8 pull-right">
                                    <input type="hidden" id="calledBy" name="calledBy" value="AssignTask">
                                    <input type="hidden" id="taskDetail" name="taskDetail" value="">
                                    <button type="submit" class="btn  btn-success" id="assignTaskSubmit">Assign Task </button>
                                    </div>
                                </div>
                                <p id="display1"></p>
                            </form>                               	   
                        </div>
                    </div>
                </div>
			</div>
					
			<div class="modal fade" id="modal-container-viewTask" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" >
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="IMAGES/Close.jpg" height=12 width=12></button>
                            <h2 class="modal-title text-center" id="myModalLabel">My Task</h2>
                         </div>
                         <div class="modal-body">
                            <form id="viewTask" class="form-horizontal well" method="Post" action="controllerThanksGiving">
                            <br>
                            <%  if (null == user.getGroupName()) {
                      			//do nothing
                      		} else{
                      			modelThanksGiving tg = new modelThanksGiving();
                      			ResultSet myTasks = null;  
    				        	myTasks = tg.fetchTaskByStatus(user.getGroupId(),"Pending");%>
 
                            <div class="form-group">
                            	<table class="table" border="2px">
                            		<tr class="table-responsive">
                                		<td class="table-responsive"><b>Task ID</b></td>
                                		<td class="table-responsive"><b>Task</b></td>
                                		<td class="table-responsive"><b>Status</b></td>
                                		<td class="table-responsive"><b>Points</b></td>
                            		</tr>
                            <% while(myTasks.next()){  //System.out.println("Enters here?");                                
                                  if (myTasks.getString("Assigned_To").equalsIgnoreCase(user.getEmailId())){ %>                                   
                                    <tr class="table-responsive">                   
                                    <td class="table-responsive"> <%= myTasks.getInt("Task_ID") %> </td>
                                    <td class="table-responsive"> <%= myTasks.getString("Task_Description") %> </td>
                                    <td class="table-responsive"> <%= myTasks.getString("Task_Status") %> </td>
                                    <td class="table-responsive"> <%= myTasks.getInt("Task_Points") %> </td>
                                    </tr>
                                  <%
                                  }
                               }}%>
                               </table>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" style="width: 200px; height: 35px" type="text" name="completedTaskId" id="completedTaskId" required="required" placeholder="Enter ID of completed task" onblur="validateTaskID()">
                                </div>
                                <div class="form-group">    
                                    <input type="hidden" id="calledBy" name="calledBy" value="TaskComplete">
                                    <button type="submit" class="btn  btn-success" id="UpdateTaskStatus" > Save </button>
                               </div>                 
                            </form>
                        </div>
                    </div>
                </div>
            </div>
			<div class="modal fade" id="modal-container-overdueTask" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" >
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="IMAGES/Close.jpg" height=12 width=12></button>
                            <h2 class="modal-title text-center" id="myModalLabel">Pending/Overdue Tasks</h2>
                         </div>
                         <div class="modal-body">
                            <form id="overdueTask" class="form-horizontal well" method="Post" action="controllerThanksGiving">
                            <br>
                            <%  if (null == user.getGroupName()) {
                      			//do nothing
                      		} else{
                            	
                      			modelThanksGiving tg = new modelThanksGiving();
                      			ResultSet myTasks = null;  
    				        	myTasks = tg.fetchTaskByStatus(user.getGroupId(),"ALL");%>
                            <div class="form-group">
                            	<table class="table" border="2px">
                            		<tr class="table-responsive">
                                		<td class="table-responsive"><b>Task</b></td>
                                		<td class="table-responsive"><b>Assigned To</b></td>
                                		<td class="table-responsive"><b>Status</b></td>
                                		<td class="table-responsive"><b>Due By</b></td>
                                		<td class="table-responsive"><b>Overdue</b></td>
                            		</tr>
                            <% while(myTasks.next()){  
                            	if((myTasks.getString("Task_Status")).equalsIgnoreCase("pending")){%>                                   
                                    <tr class="table-responsive">                   
                                    <td class="table-responsive"> <%= myTasks.getString("Task_Description") %> </td>
                                    <td class="table-responsive"> <%= myTasks.getString("Assigned_To") %> </td>
                                    <td class="table-responsive"> <%= myTasks.getString("Task_Status") %> </td>
                                    <td class="table-responsive"> <%= myTasks.getDate("Task_Completion_Date") %> </td>
                                    <% String overdue = "Yes";
                                    Date todaysDate = new Date();
                                    if ((myTasks.getDate("Task_Completion_Date")).after(todaysDate)){
                                    	overdue = "No";
                                    }%>
                                    <td class="table-responsive"> <%= overdue %> </td>
                                    </tr>
                                  <%
                                  }
                               }}%>
                               </table>
                                </div>
                                <div class="form-group">
                                    <label> * Please go to Assign task tab to reassign any task </label>
                                </div>
                                <div class="form-group">    
                                    <input type="hidden" id="calledBy" name="calledBy" value="OverdueTask">
                                </div>                 
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="modal-container-oldTasks" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" >
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="IMAGES/Close.jpg" height=12 width=12></button>
                            <h2 class="modal-title text-center" id="myModalLabel">Old Task Names</h2>
                         </div>
                         <div class="modal-body">
                            <form id="overdueTask" class="form-horizontal well" method="Post" action="controllerThanksGiving">
                            <br>
                            <%  if (null == user.getGroupName()) {
                      			//do nothing
                      		} else{
                            	
                      			modelThanksGiving tg = new modelThanksGiving();
                      			ResultSet oldTasks = null;  
    				        	oldTasks = tg.fetchParentTask(user.getGroupId());%>
                            <div class="form-group">
                            	<table class="table" border="2px">
                            		<tr class="table-responsive">
                                		<td class="table-responsive"><b>Task Name</b></td>
                                		<td class="table-responsive"><b>Task Points</b></td>
                                	</tr>
                            <% while(oldTasks.next()){  %>                                   
                                    <tr class="table-responsive">                   
                                    <td class="table-responsive"> <%= oldTasks.getString("Task_Description") %> </td>
                                    <td class="table-responsive"> <%= oldTasks.getInt("Task_Points") %> </td>
                                    </tr>
                                  <%}}%>
                               </table>
                                </div><div class="form-group">    
                                    <input type="hidden" id="calledBy" name="calledBy" value="OldTasks">
                                </div>                 
                            </form>
                        </div>
                    </div>
                </div>
            </div>
						
			
			
			<div style="position:absolute; top:300px; left:370px;" >
				<form id="displayGroupDetails" name="displayGroupDetails">
                              		
                              		<% if (null == user.getGroupName()) {
                              			//do nothing
                              		} 
                              			else { %>
                              				<table class="table" border="2px"><tr><td><b>Group Name: &nbsp; <%= user.getGroupName() %></b></td></tr></table>
                              					<table class="table" border="2px"><tr class="table-responsive"><td class="table-responsive"><b>Task</b></td>
		    										<td class="table-responsive"><b>Assigned To</b></td>
		    										<td class="table-responsive"><b>Assigned By</b></td>
		    										<td class="table-responsive"><b>Points</b></td>
		    										<td class="table-responsive"><b>Status</b></td>
                                					<td class="table-responsive"><b>Recurring</b></td>
                                					<td class="table-responsive"><b>Due Date</b></td></tr>
                           					<% 	int memberCount = (user.getIndividualGroupMembers()).length;
                           						ResultSet taskDisplay = null;
                           						modelThanksGiving tg = new modelThanksGiving();
                           						taskDisplay = tg.fetchTaskByStatus(user.getGroupId(),"ALL");                				        		
                           						int groupPoints=0;
                           						int myPoints=0;
                           						
                           						while(taskDisplay.next()){ 
                           							groupPoints = groupPoints + taskDisplay.getInt("Task_Points");
                           							
                           							if((taskDisplay.getString("Assigned_To")).equals(user.getEmailId())){
                           								myPoints = myPoints + taskDisplay.getInt("Task_Points");
                           							}%>
                           							
                        	   						<tr class="table-responsive"><td class="table-responsive"><%=taskDisplay.getString("Task_Description") %></td>
		    											<td class="table-responsive"><%=taskDisplay.getString("Assigned_To") %></td>
		    											<td class="table-responsive"><%=taskDisplay.getString("Assigned_By") %></td>
		    											<td class="table-responsive"><%=taskDisplay.getInt("Task_Points") %></td>
		    											<td class="table-responsive"><%=taskDisplay.getString("Task_Status") %></td>
                                						<td class="table-responsive"><%=taskDisplay.getString("Recurrent") %></td>
                                						<td class="table-responsive"><%=taskDisplay.getDate("Task_Completion_Date") %></td></tr>
		    									<%}%> 
		    									
		    									
		    								</table>
		    								<table class="table" border="2px">
		    									<tr class="table-responsive">
		    										<td class="table-responsive"><b>Total points for grabs </b></td>
		    										<td class="table-responsive"><b>Total points you grabbed </b></td>
		    										<td class="table-responsive"><b>Points for fair share </b></td>
		    									</tr>
		    									<tr class="table-responsive">
		    										<td class="table-responsive"><%= groupPoints %> </td>
		    										<td class="table-responsive"><%= myPoints %> </td>
		    										<td class="table-responsive"><%= groupPoints/memberCount %> </td>
		    									</tr>
		    								</table> 		    								
		    								<div class="form-group">
		    										<table class="table" border="2px">
		    											<tr class="table-responsive">
		    											<td class="table-responsive"><b></b></td>
		    											<td class="table-responsive"><b>This week's points you earned:</b></td>
		    											</tr>
		    											<tr class="table-responsive">
		    											<td class="table-responsive"></td>
		    											<% 	ResultSet tasks = tg.fetchWeeklyPoints(user.getGroupId(), user.getEmailId(), "Complete");
		    												int weeklyPoints = 0;	    														    												
		    												while(tasks.next()){
		    													weeklyPoints = tasks.getInt("total");
		    												}%>
		    												<td class="table-responsive"><%= weeklyPoints %></td>
		    											</tr>
		    										</table>                                 		
                                    		</div><%}%>
		    				</form></div>
				
       <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="JS/bootstrap.min.js"></script>
	<script src="JS/jquery.js"></script>
    <script src="JS/script.js"></script>
	<script src="JS/addbutton.js"></script> 
  </body>
</html>