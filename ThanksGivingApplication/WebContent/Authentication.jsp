<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Authenticating User Name</title>
</head>
<body>
 <%
Connection con= null;
PreparedStatement ps = null;
ResultSet rs = null;

String driverName = "com.mysql.jdbc.Driver";
String url = "jdbc:mysql://localhost:3306/";
String user = "root";
String password = "Shavi19101988";


//String sql = "select email_id from thanksgiving.login_details";

String email_id = request.getParameter("email_id");
String pwd = request.getParameter("password");
String sql;

sql = "SELECT Password FROM thanksgiving.LOGIN_DETAILS where Email_ID = \"" + email_id +"\"";

try
{
	Class.forName(driverName);
	con = DriverManager.getConnection(url, user, password);
	ps = con.prepareStatement(sql);
	rs = ps.executeQuery();
	
  while(rs.next())
  {
    String pass = rs.getString("Password");
    
    if (pwd.equals(pass))
    {
    	%>
<script> window.location("Home.jsp?email_id= <%= email_id%> &ac=nt"); </script>
<%
	}
    else
    {
 %>
 <script> window.location("LoginForm.jsp?"); </script>
 <%   	
    }
  }
  rs.close();
}
catch(Exception e)
{
  e.printStackTrace();
}    
%>

</body>
</html>