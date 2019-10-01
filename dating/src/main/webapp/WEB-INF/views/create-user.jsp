
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib uri="http://www.springframework.org/tags/form"  
  prefix="form"%> 
<%@ taglib uri="http://www.springframework.org/tags"
  prefix="spring"%>
  <%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Create User</h1>
	<p>Enter new User Details</p>
	
	<p>
		<form:form method="post" >
			<fieldset>
				<label>Username </label>
				<input name="username" type="text" required="required" />				
			</fieldset>
			
			<input type="submit" value="Submit" />
		</form:form>
	</p>
		
</body>
</html>