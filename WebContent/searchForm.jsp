<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Filter Restaurants</title>
</head>
<body>
	<form action="filterRestaurantsFromDB.action" method="POST" onsubmit="">
	<h3>Filter Based on Parameters</h3>
		<table>
			<tr>
				<td>Food</td>
				<td>
					<select name="foodRating">
						<option value="0">Select</option>
						<c:forEach items="${requestScope.ratings}" var="r">
							<option value="${r.id}">${r.name}</option>
						</c:forEach>				
					</select>
				</td>
			</tr>
			<tr>
				<td>Ambiance</td>
				<td>
					<select name="ambianceRating">
						<option value="0">Select</option>
						<c:forEach items="${requestScope.ratings}" var="r">
							<option value="${r.id}">${r.name}</option>
						</c:forEach>				
					</select>
				</td>
			</tr>
			<tr>
				<td>Service</td>
				<td>
					<select name="serviceRating">
						<option value="0">Select</option>
						<c:forEach items="${requestScope.ratings}" var="r">
							<option value="${r.id}">${r.name}</option>
						</c:forEach>				
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Search" name="save"/>
					<input type="button" value="Cancel" name="cancel" onclick="javascript:window.location='index.jsp'"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>