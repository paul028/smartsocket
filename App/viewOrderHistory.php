<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class ViewOrders
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_order_exist($username)
		{
			$viewCustomerID = "select customerID from customers where username like '$username' or emailaddress like '$username'";
			$result = mysqli_query($this->connection,$viewCustomerID);
			$custID = mysqli_fetch_row($result);
			
			$query = "select * from Orders where customerID = '$custID[0]' and orderstatus='Processed' order by orderID desc";
			$result = mysqli_query($this->connection, $query);
			$json = array();
			
			if(mysqli_num_rows($result))
			{
				while($row=mysqli_fetch_assoc($result))
				{
					$json[]=$row;
					
				}
			}
			else
			{
				$json['error'] = 'Please check your internet connection';
			}
			echo json_encode($json);
			mysqli_close($this->connection);
		}
	}
	$viewOrders = new ViewOrders();
	if(isset($_GET['username']))
	{
		$username = $_GET['username'];
		$viewOrders -> does_order_exist($username);
	}
?> 				