<?php
include 'db.php';
//posts of user, with limit
//all posts, with limit
//posts with fixed tags, with limit


$limit=(isset($_GET['limit'])&&is_numeric($_GET['limit']) ? $_GET['limit'] : 20);

if(isset($_GET['post'])){

}
else if(isset($_GET['user'])){
$user=$_GET['user'];
if (preg_match("/^[a-zA-Z0-9_\-\.]{1,}$/", $user, $matches)){$user=$matches[0];}
$result = mysqli_query($con,"SELECT username, message  FROM messages where owner=(select uid from users where username='".$user."') order by mid desc limit ".$limit);
//while($row = mysqli_fetch_array($result))
$row=mysqli_fetch_array($result);
print_r($row);
}
else if(isset($_GET['tag'])){

}
else{echo "Please read API documentation";}
?>
