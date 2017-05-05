<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function fnValidateForm(frm){
	var msg="";
	var valid=true;
	
	if(frm.restaurant[frm.restaurant.selectedIndex].value=="0"){
		msg+="<br />Plese select restaurant";
		valid=false;
	}
	if(frm.parameters[frm.parameters.selectedIndex].value=="0"){
		msg+="<br />Plese select parameter";
		valid=false;
	}
	if(!valid){
		document.getElementById("errDiv").innerHTML=msg;
	}
	return valid;
}

</script>
<title>Get Raings</title>
</head>
<body>
	<form action="getRatingsFromDB.action" method="POST" onsubmit="return fnValidateForm(this);">
	<h3>Get Ratings For a Restaurant</h3>
	<div id="errDiv" style="color: red"></div>
		<table>
			<tr>
				<td>Restaurant*</td>
				<td>
					<select name="restaurant">
						<option value="0">Select</option>
						<c:forEach items="${requestScope.restaurants}" var="r">
							<option value="${r.name}">${r.name}</option>
						</c:forEach>				
					</select>
				</td>
			</tr>
			<tr>
				<td>Parameter*</td>
				<td>
					<select name="parameters">
						<option value="0">Select</option>
						<c:forEach items="${requestScope.parameters}" var="p">
							<option value="${p.name}">${p.name}</option>
						</c:forEach>				
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Get Ratings" name="save"/>
					<input type="button" value="Cancel" name="cancel" onclick="javascript:window.location='index.jsp'"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>