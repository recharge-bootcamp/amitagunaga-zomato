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
	if(frm.name.value == "") {
		msg+="<br />Plese enter your name";
		valid=false;
	}
	if(frm.restaurant[frm.restaurant.selectedIndex].value=="0"){
		msg+="<br />Plese select restaurant";
		valid=false;
	}
	if(frm.parameters[frm.parameters.selectedIndex].value=="0"){
		msg+="<br />Plese select parameter onwhich you want to rate";
		valid=false;
	}
	if((frm.ratings[0].checked == false) && (frm.ratings[1].checked == false) && 
			(frm.ratings[2].checked == false) && (frm.ratings[3].checked == false) && (frm.ratings[4].checked == false)) {
		msg+="<br />Plese provide the ratings for a restaurant!";
		valid=false;
	}
	if(frm.review.value.trim() == "") {
		msg += "<br />Please provide review on the restaurant";
		valid = false;
	}
	if(!valid){
		document.getElementById("errDiv").innerHTML=msg;
	}
	return valid;
}

</script>
<title>Review A Restaurant</title>
</head>
<body>
	<h3>Review A Restaurant</h3>
	<div id="errDiv" style="color: red"></div>
	<form action="addReviewsToDB.action" method="POST" onsubmit="return fnValidateForm(this);">
		<table>
			<tr>
				<td>Name*</td>
				<td>
					<input type="text" name="name"/>
				</td>
			</tr>
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
				<td>Rating*</td>
				<c:forEach items="${requestScope.ratings}" var="rating">
					<td><input type="radio" name="ratings" value="${rating.id}">${rating.name}</td>
				</c:forEach>
			</tr>
			<tr>
				<td>Review*</td>
				<td>
					<textarea rows="5" cols="15" name="review">
					</textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Save" name="save"/>
					<input type="button" value="Cancel" name="cancel" onclick="javascript:window.location='index.jsp'"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>