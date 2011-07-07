<%@ page import="com.accenture.geoquiz.model.*, java.sql.Timestamp, java.util.List;" %>
<% Event event = (Event) request.getAttribute("event"); %>
<% List<Place> places = (List<Place>) request.getAttribute("places"); %>
<% List<User> responders = (List<User>) request.getAttribute("responders"); %>
<% Status status = (Status) request.getAttribute("status"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>  
	<title>HR event</title> 
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

<h3>Event <%=event.getId() %></h3>
<div id="form">
<form action="eventSubmit" method="get">
	<input type="hidden" name="eventId" value="<%=event.getId() %>" />
	Title: <input type="text" name="title" value="<%=event.getTitle() %>" /> <br />
	<% String s = event.getEventDate().toString(); %>
	Date: <input type="text" name="date" value="<%=s.substring(0, 10) %>" />yyyy-mm-dd<br />
	<% if (event.isOpen()) {%>
		Open: <input type="checkbox" name="open" value="true" checked /> <br />
	<%} else { %>
		Open: <input type="checkbox" name="open" value="true" /> <br />
	<%} %>
	
	<div id="placeList">
	<h5>Places</h5>
	<% for (int i = 0; i < places.size(); i++) {
		Place p = places.get(i);%>
		<%if (p.getId() == event.getPlace().getId()) { %>
			<input type="radio" name="placeId" value="<%=p.getId() %>" checked /><%=p.getName() %> <br />
		<%} else { %>
			<input type="radio" name="placeId" value="<%=p.getId() %>" /><%=p.getName() %> <br />
	<%} } %>
	</div>
	
	<input type="submit" value="Save changes" /> <br />
	
</form>
<a href="home">Abandon changes and go home</a>
<div id="questionList">
<h5>List of questions</h5>
<%if (event.getId() < 0) { %>
	Create the event before adding questions
<%} else { %>
	<table border="1">
	<tr>
	<th>Question</th><th>Post Description</th><th>Activation code</th><th>Remove</th>
	</tr>
	<% for (int i = 0; i < event.getQuestions().size(); i++) {
		Question q = event.getQuestions().get(i);%>
		<tr>
		<td><%=q.getQuestion() %></td>
		<td><%=q.getPostDescription() %></td>
		<td><%=q.getActivationCode() %></td>
		<td><a href="removeEventQuestion?eventId=<%=event.getId() %>&questionId=<%=q.getId() %>"> (rm)</a></td>
		</tr>
	<%} %>
	</table>
	<a href="eventQuestion?eventId=<%=event.getId() %>">+New question</a>
<%} %>
</div>
</div>
<div id="responders">
<h5>Responders</h5>
	<table border="1">
	<tr>
	<th>Nickname</th><th>E-mail</th><th>Phone</th><th>Answered</th><th>Finish time</th>
	</tr>
	<% for (int i = 0; i < responders.size(); i++) { 
		User u = responders.get(i); %>
		<tr>
		<td><%=u.getNickname() %></td>
		<td><%=u.getEmail() %></td>
		<td><%=u.getPhone() %></td>
		<td><%=u.getAnswered() %></td>
		<td><%=u.getFinishTime() %></td>
		</tr>
	<% } %>
	</table>
</div>
</body>
</html>