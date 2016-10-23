<html>
<head><title>Hello World</title></head>
<body>
Hello World!<br/>
<p>
   TODAY IS: <%= (new java.util.Date()).toLocaleString()%>
</p>
<%
out.println("Your IP address is " + request.getRemoteAddr());
%>
</body>
</html>