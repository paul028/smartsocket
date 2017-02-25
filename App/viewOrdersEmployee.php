
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
         
         function viewOrders()
		{
			$query = "select OrderID, o.CustomerID, DateOrdered, c.FirstName, c.LastName, o.OrderType from Orders o join customers c on (c.CustomerID = o.CustomerID) where orderstatus like 'Placed' order by orderID asc";
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
	$viewOrders -> viewOrders();
?> 				