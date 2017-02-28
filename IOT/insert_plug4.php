<?php
	$voltage=$_POST['voltage'];
	$current=$_POST['current'];
	$voltage3=$_POST['voltage3'];
	$current3=$_POST['current3'];
	$db_host="localhost";
	$db_user="edc";	
	$db_pw="test2016";
	$db_maindb="smartsocket";
	
	$dbconnection = new mysqli($db_host, $db_user, $db_pw, $db_maindb);
	$dbquery = 	'INSERT INTO readings(socket_id,voltage,current) VALUES (1,'.$voltage.','.$current.'),(3,'.$voltage3.','.$current3.');';
	$dbconnection->query($dbquery);
?>
