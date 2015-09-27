<link rel="stylesheet" href="styles/styleGreen.css" type="text/css" media="screen" />

<?php
include 'db.php';

?>
<div align="right">
<?php
if(isset($_SESSION['auth'])){
?>
  Hello <?php echo $_SESSION['user']; ?> <a href="logout.php">Sign out</a>
<?php
}else{
?>
<ul class="try">
<a href="register.php">Register</a>
</ul>
<form name="input" action="login.php" method="post">
<h2>Username:</h2><input type="text" name="user" class="input" ><br>
<h2>Password:</h2><input type="password" name="pass" class="input" ><br>
<br>
<input type="submit" value="Login" class="try">
</div>
</form>
<?php
}
?>
</div>

