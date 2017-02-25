<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class ProcessOrder
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_order_exist($orderid)
		{
			$checkOrders = "UPDATE orders SET OrderStatus = 'Processed' where orderID=$orderid";
			$checkResult = mysqli_query($this->connection,$checkOrders);
			if($checkResult)
			{
				$query = "select * from order_details join products on (order_details.ProductID=products.ProductID) where orderID=$orderid";
				$result = mysqli_query($this->connection, $query);
				$orderinfo = array();
				while($row=mysqli_fetch_assoc($result))
				{
					$orderinfo[]=$row;
					$query3 = "update Products set Sold = sold+$row[Quantity] where productid=$row[ProductID]";
					$result5 = mysqli_query($this->connection, $query3);
					if($result5)
					{
						$json['success']= 'Successfully Processed Order!';
					}
				}
				
			}
			else
			{
				$json['error'] = 'No Order Found!';
			}
			echo json_encode($json);
			mysqli_close($this->connection);
		}
	}
	$processOrder = new ProcessOrder();
	if(isset($_POST['orderID']))
	{
		$orderid = $_POST['orderID'];
		if(!empty($orderid) )
		{
			$processOrder -> does_order_exist($orderid);
		}
	}
?> 				