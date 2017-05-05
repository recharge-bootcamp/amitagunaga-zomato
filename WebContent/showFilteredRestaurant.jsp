<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Filtered Restaurants</title>
</head>
<body>
	<a href="javascript:window.location='index.jsp'">Home</a><br />
	<c:choose>
		<c:when test="${finalResultList.size() eq 0}">
			No Restaurants Available
		</c:when>	
		<c:otherwise>
			<table>
			<tr>
				<th>RESTAURANT</th>
				<th>PARAMETER</th>
			</tr>		
			<c:forEach items="${finalResultList}" var="res">
				<tr>
			    	<td>${res.restaurantName}</td>
			    	<td>${res.parameter}</td>
			    </tr>
			</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>