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
         
          function does_order_exist($userOrderID)
		{
			
			$query = "select * from Orders";
			$result = mysqli_query($this->connection, $query);
			$json = array();
			
			if(mysqli_num_rows($result))
			{
				$viewOrders = "Select * from orders join order_details on (orders.OrderID = order_details.OrderID) join products on (order_details.productID = products.ProductID) where order_details.orderID='$userOrderID'";
				$viewOrderResult = mysqli_query($this->connection, $viewOrders);
				while($row=mysqli_fetch_assoc($result))
				{
					if(mysqli_num_rows($viewOrderResult))
					{
						while($row2=mysqli_fetch_assoc($viewOrderResult))
						{
							$json[]=$row2;
						}
					}
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
	if(isset($_GET['orderID']))
	{
		$userOrderID = $_GET['orderID'];
		$viewOrders -> does_order_exist($userOrderID);
	}
?> 				