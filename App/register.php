<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class Register
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_user_exist($username,$password,$emailaddress,$firstname,$lastname,$address)
			{
				$query = "select * from user_account where email_address = '$emailaddress' OR username='$username'";
				$result = mysqli_query($this->connection, $query);
				$row_cnt = $result->num_rows;

				if ($row_cnt > 0) {

				   $json['error'] = 'Email or Username already in use.' ;

				}
				else
				{
					$fname = ucwords(strtolower($firstname));
					$lname = ucwords(strtolower($lastname));
					$useremailadd = ucwords(strtolower($emailaddress));
					$useraddress = ucwords(strtolower($address));
					$query2 = "insert into user_account(username,password,email_address,First_Name,Last_Name,address,outlet_id) values ('$username','$password','$useremailadd','$fname','$lname','$useraddress','1')";
					$is_inserted = mysqli_query($this->connection, $query2);
					if($is_inserted == 1)
					{
						$name = ucwords(strtolower($firstname));
						$json['success'] = ''.$name;
					}
					else
					{
						$json['error'] = ' An error occured. Please check your internet fake ' ;
						}
				}
					
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
	}
	$register = new Register();
	if(isset($_POST['username'],$_POST['password'],$_POST['emailaddress'],$_POST['firstname'],$_POST['lastname'],$_POST['address']))
	{
		$username = $_POST['username'];
		 $password = $_POST['password'];
		$emailaddress = $_POST['emailaddress'];
		$firstname = $_POST['firstname'];
		$lastname = $_POST['lastname'];
		$address = $_POST['address'];
		
		if(!empty($username) && !empty($password) && !empty($emailaddress) && !empty($firstname) && !empty($lastname) && !empty($address))
		{
			$register -> does_user_exist($username,$password,$emailaddress,$firstname,$lastname,$address);
		}
		else
		{
			echo json_encode("Complete all textfield first!");
		}
	}
?> 						