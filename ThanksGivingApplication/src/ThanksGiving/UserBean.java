/*##########################################################################################
# FILE NAME        : UserBean.java                                                         #
# FILE DESCRIPTION : a place holder for user information for ThanksGiving application      #
# CREATED BY       : Team ThanksGiving                                                     #
# LAST UPDATED     : November 07, 2014                                                     #
# COURSE NAME      : P565 Software Engineering                                             #
# ORGANIZATION     : Indiana University,Bloomington                                        #
############################################################################################*/

package thanksGiving;

import java.sql.ResultSet;
import java.sql.SQLException;

//import thanksGiving.modelThanksGiving.DisplayDetails;
//import thanksGiving.modelThanksGiving.ModifyDetails;

public class UserBean {

	private String emailId;
	private String pwd;
	private String fName;
	private String lName;
	private boolean isValidUser;
	
	private String groupName = null;
	private int groupId;
	private String groupMembers = null;
	private ResultSet taskDetails = null;
	
	
	public ResultSet getTaskDetails() throws SQLException {
		return taskDetails;
	}
	public void setTaskDetails(ResultSet taskDetails) {
		this.taskDetails = taskDetails;
	}
	public String getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(String groupMembers) {
		this.groupMembers = groupMembers;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public boolean isValidUser() {
		return isValidUser;
	}
	public void setValidUser(boolean isValidUser) {
		this.isValidUser = isValidUser;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String[] getIndividualGroupMembers(){
		String[] individualMembers = getGroupMembers().split(";");
		return individualMembers;
	}
	
}
