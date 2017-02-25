<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class AddCartItem
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_item_exist($productname,$username,$quantity)
		{
			$viewCustomerID = "select customerID from customers where username like '$username' or emailaddress like '$username'";
			$result = mysqli_query($this->connection,$viewCustomerID);
			$custID = mysqli_fetch_row($result);
			
			$viewproductID = "select productID from products where productname like '$productname'";
			$result2 = mysqli_query($this->connection,$viewproductID);
			$itemID = mysqli_fetch_row($result2);
			
			$query = "insert into cart (customerID,productID,quantity,timeadded) values ('$custID[0]','$itemID[0]','$quantity',now())";
			$result3 = mysqli_query($this->connection,$query);
			if($result3)
			{
				
				$json['success'] = 'Added to cart';
			}
			else
			{
				$json['error'] = 'Please check your internet connection';
			}
			echo json_encode($json);
			mysqli_close($this->connection);
		}
	}
	$addItem = new AddCartItem();
	if(isset($_POST['productname'],$_POST['username'],$_POST['quantity']))
	{
		$productname = $_POST['productname'];
		$username = $_POST['username'];
		$quantity = $_POST['quantity'];
		$addItem -> does_item_exist($productname,$username,$quantity);
	}
?> 				