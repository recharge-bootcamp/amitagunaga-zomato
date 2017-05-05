<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	function fnValidate(frm) {
		var valid = true;
		var msg = "";
		if(frm.name.value == "") {
			msg += "<br />Please provide Restaurant Name";
			valid = false;
		}
		if(frm.address.value.trim() == "") {
			msg += "<br />Please mention the address of the restaurant";
			valid = false;
		}
		if(!valid) {
			document.getElementById("errDiv").innerHTML=msg;
		}
		return valid;
	}
</script>
<title>Add Restaurant</title>
</head>
<body>
	<h3>Add Restaurant</h3>
	<div id="errDiv" style="color: red"></div>
	<form action="addRestaurantToDB.action" method="POST" onsubmit="return fnValidate(this);">
		<table>
			<tr>
				<td>Restaurant Name*</td>
				<td>
					<input type="text" name="name"/>
				</td>
			</tr>
			<tr>
				<td>Address*</td>
				<td>
					<textarea rows="8" cols="15" name="address">
					</textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Add" name="add"/>
					<input type="button" value="Cancel" name="cancel" onclick="javascript:window.location='index.jsp'"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>