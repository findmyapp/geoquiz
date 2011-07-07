<%@ page import="com.accenture.geoquiz.model.*, java.util.List;" %>
<% List<Event> events = (List<Event>) request.getAttribute("events"); %>
<% List<Question> questions = (List<Question>) request.getAttribute("questions"); %>
<% List<Place> places = (List<Place>) request.getAttribute("places"); %>
<% Status status = (Status) request.getAttribute("status"); %>
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
<div id="status">
<% if (status.isError()) { %>
	<div id="errorStatus"><%=status.getNotification() %></div>
<% } else { %>
	<div id="okStatus"><%=status.getNotification() %></div>
<% } %>
</div>

<div id="eventList">
	<h3>Events</h3>
	<% for (int i = 0; i < events.size(); i++) { 
		Event e = events.get(i);
		if (e.isOpen()) {%>
			<div id="open">
		<%} else { %>
			<div id="closed">
		<%} %>
		<a href="event?eventId=<%=e.getId()%>"><%= e.getTitle() %></a><br />
		</div>
	<% } %>
	<a href="event?eventId=-1">+Add new event</a>
</div>
<div id="questionList">
	<h3>Questions</h3>
	<table border="1">
	<tr><th>Question</th><th>Answer</th><th>Change</th><th>Remove</th></tr>
	<% for (int i = 0; i < questions.size(); i++) { 
		Question q = questions.get(i); %>
		<tr>
		<form action="editQuestion" method="get">
		<input type="hidden" name="id" value="<%=q.getId() %>" />
		<td>
		<input type="text" name="question" value="<%=q.getQuestion() %>" />
		</td>
		<td>
		<input type="text" name="answer" value="<%=q.getAnswer() %>" />
		</td>
		<td>
		<input type="submit" value="change question" />
		</td>
		<td>
		<a href="removeQuestion?questionId=<%=q.getId() %>">(rm)</a>
		</td>
		</form>
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
	<tr><th>Name</th><th>Change</th></tr>
	<% for (int i = 0; i < places.size(); i++) { 
		Place p = places.get(i); %>
		<tr>
		<form action="editPlace" method="get">
		<input type="hidden" name="id" value="<%=p.getId() %>" />
		<td>
		<input type="text" name="name" value="<%=p.getName() %>" />
		</td>
		<td>
		<input type="submit" value="Change place" />
		</td>
		</form>
		</tr>
	<% } %>
	<tr>
	<form action="addPlace" method="get">
	<td><input type="text" name="name" /></td>
	<td><input type="submit" value="Add place"/></td>
	</form>
	</tr>
	</table>	
</div>
</body>
</html>