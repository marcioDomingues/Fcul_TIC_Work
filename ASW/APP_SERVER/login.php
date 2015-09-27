<?php 
include 'db.php';
$user=$_POST['user'];
$pass=$_POST['pass'];
if(isset($user)&&isset($pass)){
$pass=sha1($pass);
if (preg_match("/^[a-zA-Z0-9_\-\.]{1,}$/", $user, $matches)) {$user=$matches[0];}
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
Bad Username!
<input type="button" value="Back" onclick="goBack()">

</body>
</html>
<?php
}
if(strlen($user)==0) die;
$result = mysqli_query($con,"SELECT count(*) FROM users where username='".$user."' and password='".$pass."'");
$row = mysqli_fetch_array($result);
if(isset($row['0'])&&$row['0']==1){$_SESSION['auth']=1;$_SESSION['user']=$user;}
header("Location: log.php");
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
Username or Password not defined!
<input type="button" value="Back" onclick="goBack()">

</body>
</html>
<?php
}
