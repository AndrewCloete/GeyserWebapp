<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>GeyserSense Login</title>
	
<style type="text/css">
	table {
		padding: 10px;
		}
	
	th{
		text-align: right;
		vertical-align: top;
		padding-right: 10px;

	}
	
	td{
		vertical-align: top;
	}
	
	table.center {
    margin-left:auto; 
    margin-right:auto;
  }
	
</style>
	
</head>
<body>

<h1 style="text-align:Center">Status</h1>

<p align="center">
<img src="images/geysericonhead.png" alt="Just to get an idea" style="vertical-align: middle;" width="500" height="364">
<table class="center">
<tr>
	<th>Geyser ID:</th>
	<td>
 		<%= request.getAttribute("geyserID") %>
 	</td>
</tr>	

<tr>
	<th>Internal temperature:</th>
	<td>
 		<%= request.getAttribute("internalTemp") %>
 	</td>
</tr>	

<tr>
	<th>Element State:</th>
	<td>
 		 <%= request.getAttribute("elementState") %>
 	</td>
</tr>	
</table>
</p>

<form method="POST" action="geyserstatus" >
<table class="center">
<tr>
	<th>Geyser ID:</th>
	<td>
 		 <input type="text" name="geyser_id_box" value=<%= request.getAttribute("geyser_id_box") %>><br>
 	</td>
</tr>
</tr>
<tr>
	<th>Element mode:</th>
	<td>
 		 <select name = "element_select">
 		 	<option value="-">--</option>
 		 	<option value="OFF">OFF</option>
 		 	<option value="ON">ON</option>
 		 	<option value="AUTO">AUTO</option>
 		 </select>
 	</td>
</tr>
<!--  
<tr>
	<th>Switch valve:</th>
	<td>
 		 <select name = "valve_select">
 		 	<option value="ON">ON</option>
 		 	<option value="OFF">OFF</option>
 		 </select>
 	</td>
</tr>
-->
<tr>
	<th></th>
	<td>
 		 <input type="submit" value="Refresh"><br>
 	</td>
</tr>	
</table>
</form>


<p align="center">
<img src="images/geysericonfoot.png" alt="Look and feel" style="vertical-align: middle;" width="250" height="100">
</p>

</body>
</html>