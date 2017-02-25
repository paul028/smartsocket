<?php
	$voltage=$_POST['voltage'];
	$current=$_POST['current'];

	$db_host="localhost";
	$db_user="edc";
	$db_pw="test2016";
	$db_maindb="smartsocket";
	
	$dbconnection = new mysqli($db_host, $db_user, $db_pw, $db_maindb);
	$dbquery = 	'INSERT INTO readings(socket_id,voltage,current) VALUES (1,'.$voltage.','.$current.');';
	$dbconnection->query($dbquery);
?>
