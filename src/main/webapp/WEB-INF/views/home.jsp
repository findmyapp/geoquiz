<%@ page import="com.accenture.geoquiz.model.*, java.util.List;" %>
<% List<Event> events = (List<Event>) request.getAttribute("events"); %>
<% List<Question> questions = (List<Question>) request.getAttribute("questions"); %>
<% List<Place> places = (List<Place>) request.getAttribute("places"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>  
	<title>HR home</title> 
	<link rel="shortcut icon" href="favicon.ico" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="description" content="" />  
	<link href="css/stil.css" media="screen" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script> 
</head> 
<body>
<div id="eventList">
	<h3>Events</h3>
	<% for (int i = 0; i < events.size(); i++) { 
		Event e = events.get(i); %>
	
		<a href="event?eventId=<%=e.getId()%>"><%= e.getTitle() %></a><br />
	<% } %>
	<a href="event?eventId=-1">+Add new event</a>
</div>
<div id="questionList">
	<h3>Questions</h3>
	<table border="1">
	<tr><th>Question</th><th>Answer</th><th>Remove</th></tr>
	<% for (int i = 0; i < questions.size(); i++) { 
		Question q = questions.get(i); %>
		<tr>
		<td><%=q.getQuestion() %></td>
		<td><%=q.getAnswer() %></td>
		<td><a href="removeQuestion?questionId=<%=q.getId() %>">(rm)</a></td>
		</tr>
	<% } %>
	<tr>
	<form action="addQuestion" method="get">
	<td><input type="text" name="question" /></td>
	<td><input type="text" name="answer" /></td>
	<td><input type="submit" value="add question"/></td>
	</form>
	</tr>
	</table>
</div>
<div id="placeList">
	<h3>Places</h3>
	<table border="1">
	<tr><th>Name</th><th>Remove</th></tr>
	<% for (int i = 0; i < places.size(); i++) { 
		Place p = places.get(i); %>
		<tr>
		<td><%=p.getName() %></td>
		<td><a href="removePlace?placeId=<%=p.getId() %>">(rm)</a></td>
		</tr>
	<% } %>
	<tr>
	<form action="addPlace" method="get">
	<td><input type="text" name="name" /></td>
	<td><input type="submit"/></td>
	</form>
	</tr>
	</table>	
</div>
</body>
</html>