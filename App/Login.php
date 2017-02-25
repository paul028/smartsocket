<?php
	require_once 'connection.php';
	header('Content-Type: application/json');
	class Login
	{
		private $db;
		private $connection;
		function __construct()
		{
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
         
         function does_user_exist($email,$password)
		{
			$query = "select * from user_account where email_address = '$email'and Password = '$password' OR username = '$email' and Password = '$password'";
			$result = mysqli_query($this->connection,$query);
                        $row = mysqli_fetch_row($result);
			if(mysqli_num_rows($result) > 0)
                        {                                
                                $json['success'] = ''.$row[1];
			}else
			{
				$json['error'] = ' Wrong username or password ';
			}
			echo json_encode($json);
			mysqli_close($this->connection);
		}
	}
	$login = new Login();
	if(isset($_POST['username'],$_POST['password']))
	{
		$email = $_POST['username'];
		$password = $_POST['password'];
		if(!empty($email) && !empty($password))
		{
			$login -> does_user_exist($email,$password);
		}
		else
		{
                        $jsonerror[error2] = 'You must enter username and password';
			echo json_encode($jsonerror);
		}
	}
?> 												