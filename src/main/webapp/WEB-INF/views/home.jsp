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
<h1>Geo-Quiz Home</h1>
<div id="sitemap">Home</div>
<div id="space"></div>
<div id="leftList">
<div id="eventList">
	<table>
	<tr><th>Event name</th><th>Place</th></tr>
	<% for (int i = 0; i < events.size(); i++) { 
		Event e = events.get(i); %>
		<tr>
		<td>
		<% if (e.isOpen()) {%>
			<a href="event?eventId=<%=e.getId()%>" id="linkOpen"><%= e.getTitle() %></a>
		<%} else { %>
			<a href="event?eventId=<%=e.getId()%>" id="linkClosed"><%= e.getTitle() %></a>
		<%} %>
		</td><td>
		<%=e.getPlace().getName() %>
		</td>
		</tr>
	<% } %>
	</table>
	<a href="event?eventId=-1">+Add new event</a>
</div>
<div id="placeList">
	<table>
	<tr><th>Place name</th><th>Action</th></tr>
	<% for (int i = 0; i < places.size(); i++) { 
		Place p = places.get(i); %>
		<tr>
		<form action="editPlace" method="post">
		<input type="hidden" name="id" value="<%=p.getId() %>" />
		<td>
		<input type="text" name="name" value="<%=p.getName() %>" />
		</td>
		<td>
		<input type="submit" value="Change" />
		</td>
		</form>
		</tr>
	<% } %>
	<tr>
	<form action="addPlace" method="post">
	<td><input type="text" name="name" /></td>
	<td><input type="submit" value="Add place"/></td>
	</form>
	</tr>
	</table>	
</div>
</div>
<div id="rightList">
<div id="questionList">
	<table>
	<tr><th>Question</th><th>Answer</th><th>Action</th><th>Remove</th></tr>
	<% for (int i = 0; i < questions.size(); i++) { 
		Question q = questions.get(i); %>
		<tr>
		<form action="editQuestion" method="post">
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
		<a href="javascript:deleteQuestion(<%=q.getId() %>)">(rm)</a>
		</td>
		</form>
		</tr>
	<% } %>
	<tr>
	<form action="addQuestion" method="post">
	<td><input type="text" name="question" /></td>
	<td><input type="text" name="answer" /></td>
	<td><input type="submit" value="add question"/></td>
	<td></td>
	</form>
	</tr>
	</table>
</div>
</div>
<div style="clear:both"></div>
</div>
</div>
<form name="deleteform" method="post" action="removeQuestion">
<input type="hidden" name="questionId" />
</form>
<script language="JavaScript" type="text/javascript">
<!--
function deleteQuestion ( id )
{
  document.deleteform.questionId.value = id ;
  document.deleteform.submit() ;
}
-->
</script>
</body>
</html>