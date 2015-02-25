<!-- *######################################################################################
# FILE NAME        : LoginForm.jsp			                                               #
# FILE DESCRIPTION : initial login page to access ThanksGiving application                 #
# CREATED BY       : Team ThanksGiving                                                     #
# LAST UPDATED     : November 07, 2014                                                     #
# COURSE NAME      : P565 Software Engineering                                             #
# ORGANIZATION     : Indiana University,Bloomington                                        #
#######################################################################################* -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="IMAGES/favicon.png">
    
<!--  Include -->

    <title>LogIn Page</title>
	
	<!-- Bootstrap core CSS -->
    <link href="CSS/bootstrap.css" rel="stylesheet">
	<link href="CSS/thanksgiving.css" rel="stylesheet">
	<link href="CSS/full-slider.css" rel="stylesheet">
	<link href="CSS/bootstrap.min.css" rel="stylesheet">
	
	<script type="text/javascript">
	function validateEmailId()
	{ 
		if(document.getElementById('useremail').value == document.getElementById('emailconfirm').value)
			return true;
		else{
			alert("Email IDs do not match, please enter again");
			document.getElementById('useremail').value = null;
			document.getElementById('emailconfirm').value = null;
			document.getElementById('useremail').focus();
			return false;
			}
	}
	
	function validatePwd()
	{ 
		if(document.getElementById('userpassword').value == document.getElementById('confirmpassword').value)
			return true;
		else{
			alert("Passwords do not match, please enter again");
			document.getElementById('userpassword').value = null;
			document.getElementById('confirmpassword').value = null;
			document.getElementById('userpassword').focus();
			return false;
			}
	}
	
	function validateName(elem)
	{	
		
			var regex = /^[a-zA-Z]{2,15}$/;
		    
			if(elem.value == null || elem.value == ''){
				//do nothing
				return true;
				}
			else if (regex.test(elem.value)) {
		        return true;
		    }
		    else {
		    	alert("Only alphabets allowed for this field, with minimum 2 characters and a maximum of 15");
		    	elem.value = null;
		    	elem.focus();
		        return false;
		    }
		}
	
		
	</script>
	</head>

  <body>
	 		<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          
          <a class="navbar-brand" href="#">
		  <img class="img-responsive" alt="" src="IMAGES/thanksgiving.jpg">
		  </a>
		  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		   <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
				</button>
		
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav"></ul>
		 </div>
			
			<div class="nav navbar-nav navbar-right">
            <a class="btn btn-primary btn-sm" href="#modal-container-logIn" role="button" data-toggle="modal">Log In</a>
            <a class="btn btn-info btn-sm" href="#modal-container-signUp" role="button" data-toggle="modal" >Sign Up</a>
          </div>
          </div>
        </div><!--/.nav-collapse -->
		</div>
		
		
		<header id="myCarousel" class="carousel slide">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for Slides -->
        <div class="carousel-inner">
            <div class="item active">
                <!-- Set the first background image using inline CSS below. -->
                <div class="fill" style="background-image:url('IMAGES/carousel1.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Manage Daily Chores</h2>
                </div>
            </div>
            <div class="item">
                <!-- Set the second background image using inline CSS below. -->
                <div class="fill" style="background-image:url('IMAGES/carousel2.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Doing Chores has never been FUN</h2>
                </div>
            </div>
            <div class="item">
                <!-- Set the third background image using inline CSS below. -->
                <div class="fill" style="background-image:url('IMAGEScarousel3.JPG');"></div>
                <div class="carousel-caption">
                    <h2>Only ThanksGiving, no more Bickering</h2>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>

    </header>   
    
  <div class="modal fade" id="modal-container-logIn" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
                            <h2 class="modal-title text-center" id="myModalLabel">Log In</h2>
                        </div>
                        <div class="modal-body">
                            <form id="LogIn" class="form-horizontal well" method="post" action="controllerThanksGiving">

                                <div class="form-group">
                                    <label for="email" class="col-sm-4 control-label">Email</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="email_id" id="email_id" placeholder="Enter Email" type="email" required="required">
                                    </div>
                                </div>
                               
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Password</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" type="password" name="password" id="password" placeholder="Enter Password" required="required">
                                    </div>
                                </div>
                     <div class="form-group">
                                    <div class="col-sm-8 pull-right">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn  btn-success" id="SignIn_Submit" onclick="submit">Sign In </button>
                                    </div>
                                </div>
                                <p id="display1"></p>
                            </form>
                            
                        </div>
                    </div>
                </div>
			</div>
			
				
			<div class="modal fade" id="modal-container-signUp" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div style="padding-bottom:1%" class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
                            <h2 class="modal-title text-center" id="myModalLabel1">Sign up</h2>
                        </div>
                        <div class="modal-body">
                            <form id="signUp" class="form-horizontal well" method="post" action="controllerThanksGiving">

                                <div class="form-group">
                                    <label class="col-sm-4 control-label">First Name</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" type="text" name="fname" id="fname" placeholder="First Name" required="required" onblur="validateName(this)">
                                    </div>
                                </div>
								<div class="form-group">
                                    <label class="col-sm-4 control-label">Last Name</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" type="text" name="lname" id="lname" placeholder="Last Name" required="required" onblur="validateName(this)">
                                    </div>
                                </div>
								<div class="form-group">
                                    <label for="email" class="col-sm-4 control-label">Email</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="useremail" id="useremail" placeholder="Enter Email" type="email" required="required">
                                    </div>
                                </div>
								<div class="form-group">
                                    <label for="email" class="col-sm-4 control-label"> Confirm Email</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="emailconfirm" id="emailconfirm" placeholder="Renter Email" type="email" required="required" onblur="validateEmailId()">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-4 control-label">Password</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="password" name="userpassword" id="userpassword" placeholder="Enter Password" required="required">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-4 control-label">Confirm Password</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="password" name="confirmpassword" id="confirmpassword" placeholder="Confirm Password" required="required" onblur="validatePwd()">
                                    </div>
                                    <input type="hidden" name="registered" id="registered" value="true">
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-8 pull-right">
                                    	<input type="hidden" id="calledBy" name="calledBy" value="CreateUser">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn  btn-success" id="Signup_Submit" onclick="">Sign Up </button>
                                    </div>
                                </div>
                            </form>
                           
                        </div>
                    </div>
                </div>
			</div>
   
	
		
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="JS/bootstrap.min.js"></script>
	<script src="JS/jquery.js"></script>
    <script src="JS/script.js"></script>
	<script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>

  </body>
</html>
	
