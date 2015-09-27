

<link rel="stylesheet" href="styles/styleGreen.css" type="text/css" media="screen" />


<?php 
//add user
//verify caracteres especiais
//verifica se ja existe
//saltamos a validacao do mail por causa do php 5.0.2

$page=(isset($_GET['page']) ? $_GET['page'] : null);
if($page=="reg"){
	include 'db.php';
	$user=$_POST['user'];
	$pass=$_POST['pass'];
//$mail=$_POST['mail'];
$mail="user@mail.com";//not used for any purpose
if(isset($user)&&isset($pass)&&isset($mail)){
	$pass=sha1($pass);
if (preg_match("/^[a-zA-Z0-9_\-\.]{1,}$/", $user, $matches)){$user=$matches[0];}//&&filter_var($mail, FILTER_VALIDATE_EMAIL)
else{
	?>
	<html>
	<head>
		<script>
		function goBack()
		{
			window.history.back()
		}
		</script>
	</head>
	<body>
		Wrong Username or Password!
		<input type="button" value="Back" onclick="goBack()">

	</body>
	</html>
	<?php
}

$result = mysqli_query($con,"SELECT count(*) FROM users where username='".$user."' and email='".$mail."'");
$row = mysqli_fetch_array($result);
if(isset($row['0'])&&$row['0']==0){$_SESSION['auth']=1;$_SESSION['user']=$user;mysqli_query($con,"insert into users(username,password,email) values('".$user."','".$pass."','".$mail."')");

}
else{
	?>
	<html>
	<head>
		<script>
		function goBack()
		{
			window.history.back()
		}
		</script>
	</head>
	<body>
		Username already in use!
		<input type="button" value="Back" onclick="goBack()">

	</body>
	</html>
	<?php
}
header("Location: log.php");
}
}
else{
	?>
	<form name="input" action="register.php?page=reg" method="post">
		<h2>Username:</h2> <input type="text" name="user" class="input"><br>
		<!--Email: <input type="text" name="mail"><br>-->
		<h2>Password:</h2> <input type="password" name="pass" class="input"><br>
		<br>
		<input type="submit" value="Submit" class="try">
	</form>
	<?php
	die;
}
