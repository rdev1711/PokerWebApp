<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<center>
<h1>
   Odds
   </h1>
<%
Double p1Odds = (Double) request.getAttribute("p1");
Double p2Odds = (Double) request.getAttribute("p2");
Double tieOdds = (Double) request.getAttribute("tie");
out.println(p1Odds);
out.println(p2Odds);
out.println(tieOdds);
%>
</body>
</html>