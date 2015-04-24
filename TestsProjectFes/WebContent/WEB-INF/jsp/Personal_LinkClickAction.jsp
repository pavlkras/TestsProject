<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.PersonalActions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.ipsForm_required{
color:red;
}
</style>
<title>VERYFICATION</title>
</head>
<body>
	<form action="jobSeeker_authorization_event" >
		<table>
			<tr>
				<td colspan="2" align="center"><h2>Verification form</h2></td>
			</tr>
			<tr>
				<td>Passport <span class="ipsForm_required">*</span></td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td>Password <span class="ipsForm_required">*</span></td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" /></td>
			</tr>  
			<tr><td><p><%= PersonalActions.WrongResponse() %></p></td>
			</tr>
		</table>
		
	</form>
	
</body>
</html>