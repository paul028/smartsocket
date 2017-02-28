<?php
	$socket_state=$_POST['socket_state'];
	$socket_id=$_POST['socket_id'];

	$db_host="localhost";
	$db_user="edc";
	$db_pw="test2016";
	$db_maindb="smartsocket";
	
	$dbconnection = new mysqli($db_host, $db_user, $db_pw, $db_maindb);
	$dbquery = 	'UPDATE socket SET socket_state ='.$socket_state.' Where socket_id ='.$socket_id.'';
	$dbconnection->query($dbquery);
?>
