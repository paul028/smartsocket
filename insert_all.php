<?php
	$pH=$_POST['pH'];
	$conductivity=$_POST['conductivity'];
	$TSS=$_POST['TSS'];
	$Temp=$_POST['Temp'];
	
	$db_host="localhost";
	$db_user="edc";
	$db_pw="test2016";
	$db_maindb="smartsocket";
	
	$dbconnection = new mysqli($db_host, $db_user, $db_pw, $db_maindb);
	$dbquery = 	'INSERT INTO readings(socket_id,voltage,current) VALUES (1,'.$Pressure.','.$Pressure.'),(2,'.$Pressure.','.$Pressure.'),(3,'.$Pressure.','.$Pressure.');';
	$dbconnection->query($dbquery);
?>
