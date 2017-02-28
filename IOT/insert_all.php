<?php
	$pH=$_POST['pH'];
	$conductivity=$_POST['conductivity'];
	$TSS=$_POST['TSS'];
	$Temp=$_POST['Temp'];
	$Pressure=$_POST['Pressure'];
	$db_host="localhost";
	$db_user="edc";
	$db_pw="test2016";
	$db_maindb="edc";
	
	$dbconnection = new mysqli($db_host, $db_user, $db_pw, $db_maindb);
	$dbquery = 	'INSERT INTO data_logger(sensor_no,value) VALUES (1,'.$Pressure.'),(2,'.$Temp.'),(3,'.$pH.'),(4,'.$conductivity.'),(5,'.$TSS.');';
	$dbconnection->query($dbquery);
?>
