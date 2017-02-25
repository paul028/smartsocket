<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class ViewItem
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_item_exist($productname,$customerID,$quantity)
		{
			$query = "insert into cart (customerID,productID,quantity,timeadded) values ('$customerID','$productID','$quantity','now()')";
			$result = mysqli_query($this->connection,$query);
			$json = array();
			if(mysqli_num_rows($result))
			{
				
				while($row=mysqli_fetch_assoc($result))
				{
					$json=$row;
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
	$viewItem = new ViewItem();
	if(isset($_POST['productname'],$_POST['customerID'],$_POST['quantity']))
	{
		$customerID = $_POST['customerID'];
		$productname = $_POST['productname'];
		$quantity = $_POST['quantity'];
		$viewItem -> does_item_exist($productname,$customerID,$quantity);
	}
?> 				