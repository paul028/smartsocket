<?php
	$voltage=$_POST['voltage'];
	$current=$_POST['current'];
	$voltage2=$_POST['voltage2'];
	$current2=$_POST['current2'];
	$db_host="localhost";
	$db_user="edc";	
	$db_pw="test2016";
	$db_maindb="smartsocket";
	
	$dbconnection = new mysqli($db_host, $db_user, $db_pw, $db_maindb);
	$dbquery = 	'INSERT INTO readings(socket_id,voltage,current) VALUES (1,'.$voltage.','.$current.'),(2,'.$voltage2.','.$current2.');';
	$dbconnection->query($dbquery);
?>
