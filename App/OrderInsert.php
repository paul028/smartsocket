<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class InsertOrder
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_cart_exist($username,$ordertype)
		{
			$viewCustomerID = "select customerID from customers where username like '$username' or emailaddress like '$username'";
			$viewresult = mysqli_query($this->connection,$viewCustomerID);
			$custID = mysqli_fetch_row($viewresult);
			
			$checkCart = "Select * from cart where customerID='$custID[0]'";
			$CartResult = mysqli_query($this->connection,$checkCart);
			if(mysqli_num_rows($CartResult))
			{
				$query2 = "insert into Orders (CustomerID,dateOrdered,OrderType,OrderStatus) values ('$custID[0]',now(),'$ordertype','Placed')";
				$is_inserted = mysqli_query($this->connection, $query2);
				if($is_inserted == 1)
				{
					$query4 = "select orderID from orders where customerID = '$custID[0]'";
					$result4 = mysqli_query($this->connection, $query4);
					$result4json = array();
					if(mysqli_num_rows($result4))
					{
						
						while($row=mysqli_fetch_assoc($result4))
						{	
							$result4json=$row;
						}
						
						$query = "select * from Cart where customerID = '$custID[0]'";
						$result = mysqli_query($this->connection, $query);
						$orderinfo = array();
						while($row=mysqli_fetch_assoc($result))
						{
							$orderinfo[]=$row;
							$query3 = "insert into order_details (OrderID,ProductID,Quantity) values ('$result4json[orderID]','$row[ProductID]','$row[Quantity]')";
							$result5 = mysqli_query($this->connection, $query3);
						}
						$querydelete = "delete from Cart where customerID = '$custID[0]'";
						$resultdelete = mysqli_query($this->connection, $querydelete);
						$json['success']='Successfully placed order';
					}
					
				}
				else
				{
					$json['error'] = ' An error occured. Please check your internet connection ' ;
				}
			}
			else
			{
				$json['error'] = 'No Items in Cart!';
			}
			echo json_encode($json);
			mysqli_close($this->connection);
		}
	}
	$insertOrder = new InsertOrder();
	if(isset($_POST['username'],$_POST['orderTYPE']))
	{
		$username = $_POST['username'];
		$ordertype = $_POST['orderTYPE'];
		if(!empty($username))
		{
			$insertOrder -> does_cart_exist($username,$ordertype);
		}
	}
?> 				