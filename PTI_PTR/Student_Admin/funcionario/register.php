<?php 
require_once("../openconnection.php");

if(isset($_POST['submit'])){
	if(preg_match('%^[a-z0-9.]{2,20}+@[a-z.]+\.(ul)\.(pt)%',stripslashes(trim($_POST['user'])))){
		$data=escape_data($_POST['user']);
	}else{
		$data=FALSE;
		echo 'Please enter a valid email';
	}
}

$pw = $_POST['pass'];
$user = $_POST['user'];
if($user && $pw){
	$query = "SELECT idAluno FROM Alunos WHERE email='$user'";
	
	$result=$mysqli->query($query) or trigger_error("Error: ".$query);
	
	
}


//REGISTER QUERY
if($mysqli->num_rows($result)==0){
//email is not taken
	$query="INSERT INTO users(pass,user) VALUES (SHA('$pw'),$user)";
	$result=$mysqli->query($query) or trigger_error("Error: ".$query);
	
	if($mysqli->affected_rows()==1){
		//good
	}
}
else{
//email taken
}
mysql_close();
?>
