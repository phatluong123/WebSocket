<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>    
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Web Socket</title>

<script type="text/javascript" src="../js/app.js"></script>
</head>
<body>
	<textarea id="messagesTextArea" readonly="readonly" rows="16" cols="60"></textarea>
	<textarea id="usersTextArea" readonly="readonly" rows="16" cols="8"></textarea> <br>
	<input id="locationSelect" type="text" size="65"/>
	<select id="locationSelect">
		<option value="US">US</option>
		<option value="Canada">Canada</option>
		<option value="Other">Other</option>
	</select>
	<input type="button" value="Send" onClick="send()" >
</body>
</html>
