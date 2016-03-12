<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<title>
	FCI Square
</title>

</head>

<body>

<center><h1>Welcome to FCI Square</h1>
		<h1> Signup now</h1></center>

<form action = "doSignUp" method = "post" >
	
	User name: <input type="text" name = "uname" /> <br>
	Email <input type="text" name = "email" /> <br>
	Password: <input type="text" name = "pass" /> <br>
	<input type="submit" value = "Signup" />


</form>

</body>

</html>