<%@ page import="com.accenture.geoquiz.model.*, java.sql.Timestamp, java.util.List;" %>
<% Integer eventId = (Integer) request.getAttribute("eventId"); %>
<% List<Question> questions = (List<Question>) request.getAttribute("questions"); %>
<% Status status = (Status) request.getAttribute("status"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>  
	<title>new question for</title> 
	<link rel="shortcut icon" href="favicon.ico" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="description" content="" />  
	<link href="../resources/css/reset.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="../resources/css/stil.css" media="screen" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script> 
</head> 
<body>
<div id="container">
<div id="status">
<% if (status.isError()) { %>
	<div id="errorStatus"><%=status.getNotification() %></div>
<% } else { %>
	<div id="okStatus"><%=status.getNotification() %></div>
<% } %>
</div>
<div id="bodyContainer">
<h1>Add new question to event <%=eventId %></h1>
<div id="sitemap"><a href="home">Home</a>/<a href="event?eventId=<%=eventId %>">Event</a>/New question</div>
<div id="space"></div>
<% if (questions.size() > 0) { %>
	<form action="addEventQuestion" method="get">
	<div id="leftPane">
	<h2>Post information</h2>
	<input type="hidden" name="eventId" value="<%=eventId %>" />
	Post description:
	<input type="text" name="description" /><br />
	Activation code:
	<input type="text" name="activationCode" /><br />
	<input type="submit" value="save" />
	<a href="event?eventId=<%=eventId %>">cancel and go to event</a>
	</div>
	<div id="middlePane"></div>
	<div id="rightPane">
	<h2>Choose question</h2>
	<% for (int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);%>
		<input type="radio" name="questionId" value="<%=q.getId() %>" checked/><%=q.getQuestion() %><br />
	<%} %>
	</div>
	</form>
	
<%} else { %>
	There are no more questions that are not used by this event. Go to the home page and add some more!<br />
	<a href="event?eventId=<%=eventId %>">go to event</a><br />
	<a href="home">go home</a>
<%} %>
</div>
</div>
</body>
</html>