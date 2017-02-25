<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class ViewCartItem
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_cart_exist($username)
		{
			$viewCustomerID = "select customerID from customers where username like '$username' or emailaddress like '$username'";
			$result = mysqli_query($this->connection,$viewCustomerID);
			$custID = mysqli_fetch_row($result);
			
			$query = "select * from Cart where customerID = '$custID[0]'";
			$result = mysqli_query($this->connection, $query);
			$json = array();
			
			if(mysqli_num_rows($result))
			{
				$viewItem = "Select ProductName, Photo, Price, Description, Quantity from Products join cart on (products.ProductID = cart.ProductID) where customerID = '$custID[0]'";
				$viewItemresult = mysqli_query($this->connection, $viewItem);
				while($row=mysqli_fetch_assoc($result))
				{
					
					if(mysqli_num_rows($viewItemresult))
					{
						while($row2=mysqli_fetch_assoc($viewItemresult))
						{
							$json[]=$row2;
						}
					}
				}
			}
			else
			{
				$json['error'] = 'No Items in Cart.';
			}
			echo json_encode($json);
			mysqli_close($this->connection);
		}
	}
	$viewCartItem = new ViewCartItem();
	if(isset($_GET['username']))
	{
		$username = $_GET['username'];
		$viewCartItem -> does_cart_exist($username);
	}
?> 				