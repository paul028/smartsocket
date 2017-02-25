<?php
	$host="localhost"; //replace with database hostname 
	$username="root"; //replace with database username 
	$password=""; //replace with database password 
	$db_name="u968493487_shop"; //replace with database name

	$con=mysqli_connect("$host", "$username", "$password", "$db_name")or die("cannot connect"); 
	$sql = "select ProductName, Photo, Price, Description from Products where CategoryID=3"; 
	$result = mysqli_query($con,$sql);
	$json = array();
 
	if($result === FALSE) 
	{ 
		die(mysql_error());
	}
	else if(mysqli_num_rows($result))
	{
		while($row=mysqli_fetch_assoc($result))
		{
			$json[]=$row;
		}
	}
	mysqli_close($con);
	echo json_encode($json); 
?> 