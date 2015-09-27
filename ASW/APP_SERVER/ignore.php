<?php 
include 'db.php';
$user=(isset($_SESSION['user']) ? $_SESSION['user'] : null);
$userToIgnore=(isset($_GET['user']) ? $_GET['user'] : null);//or unignore if $_GET['remove']==1
if(isset($user)){
	if(isset($userToIgnore)&&preg_match("/^[a-zA-Z0-9_\-\.]{1,}$/", $userToIgnore, $matches)&&!isset($_GET['remove'])){
		$userToIgnore=$matches[0];
		$result = mysqli_query($con,"SELECT count(*) as count FROM ignored inner join users on ignored.uid_ign=users.uid where ignored.uid=(select uid from users where username='".$_SESSION['user']."') and ignored.uid_ign=(select uid from users where username='".$userToIgnore."')");
		$row=mysqli_fetch_array($result);
		if($row['count']<1){
			mysqli_query($con,"insert into ignored(uid,uid_ign) values((select uid from users where username='".$_SESSION['user']."'),(select uid from users where username='".$userToIgnore."'))");
		}
		header("Location: ignore.php");
	}
	else if(isset($userToIgnore)&&preg_match("/^[a-zA-Z0-9_\-\.]{1,}$/", $userToIgnore, $matches)&&isset($_GET['remove'])){
		$userToIgnore=$matches[0];
		mysqli_query($con,"delete FROM ignored where uid=(select uid from users where username='".$_SESSION['user']."') and uid_ign=(select uid from users where username='".$userToIgnore."')");
		header("Location: ignore.php");
	}
else{//show all to be able to unignore
	$result = mysqli_query($con,"SELECT username FROM ignored inner join users on ignored.uid_ign=users.uid where ignored.uid=(select uid from users where username='".$_SESSION['user']."')");
	while($row = mysqli_fetch_array($result))
	{
		echo "<tr><td><a href=\"ignore.php?user=".$row['username']."&remove=1\">".$row['username']."</a></td></tr><br>";
	}
	echo "<br><br><br><a href=\"messages.php\">Go back</a>";
}

}
//ignora users 
//
