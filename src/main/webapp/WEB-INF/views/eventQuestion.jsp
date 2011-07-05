<%@ page import="com.accenture.geoquiz.model.*, java.sql.Timestamp, java.util.List;" %>
<% Integer eventId = (Integer) request.getAttribute("eventId"); %>
<% List<Question> questions = (List<Question>) request.getAttribute("questions"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>  
	<title>new question for</title> 
	<link rel="shortcut icon" href="favicon.ico" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="description" content="" />  
	<link href="css/stil.css" media="screen" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script> 
</head> 
<body> 
	<h1>Add new question to event <%=eventId %></h1>
	<form action="addEventQuestion" method="get">
	<input type="hidden" name="eventId" value="<%=eventId %>" />
	Post description:
	<input type="text" name="description" /><br />
	Activation code:
	<input type="text" name="activationCode" /><br />
	
	<% for (int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);%>
		<input type="radio" name="questionId" value="<%=q.getId() %>" /><%=q.getQuestion() %><br />
	<%} %>
	<input type="submit" value="save" />
	</form>
</body>
</html>