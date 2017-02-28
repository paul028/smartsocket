<?php
$servername = 'localhost';
$username = 'edc'; // username for your database
$password = 'test2016';
$dbname = 'smartsocket'; // Name of database
$conn = mysql_connect('localhost','edc','test2016');

if (!$conn)
{
    die('Could not connect: ' . mysql_error());
}
$con_result = mysql_select_db($dbname, $conn);
if(!$con_result)
{
	die('Could not connect to specific database: ' . mysql_error());
}
$sql = "select socket_name,socket_state from socket where outlet_id=1";
$result = mysql_query($sql);
if (!$result) {
	die('Invalid query: ' . mysql_error());
}
while($row = mysql_fetch_array($result, MYSQL_ASSOC))
{
echo "" . $row["socket_name"]. "-" . $row["socket_state"]."<br>";
}

mysql_close($conn);
?>
