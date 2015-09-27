

<?php 
//add image
//magic numb non repeat name

include 'db.php';
$msg=$_POST['msg'];
if(isset($msg)){
	$user=(isset($_SESSION['user']) ? $_SESSION['user'] : 1);
	$result = mysqli_query($con,"SELECT uid FROM users where username='".$user."'");
	$row = mysqli_fetch_array($result);
	if(isset($row['uid'])){$user=$row['uid'];}
strip_tags($msg);//remove html tags,xss shall not pass ))
mysqli_query($con,"insert into messages(owner,message) values(".$user.",'".base64_encode($msg)."')");
$result=mysqli_query($con,"select mid from messages where owner=".$user." and message like '".base64_encode($msg)."' order by mid desc limit 1");
$rowX = mysqli_fetch_array($result);
$mid=$rowX['mid'];
//images
$tmp=explode(".",$_FILES['uploadedfile']['name']);
if(isset($_FILES['uploadedfile'])&&strlen($tmp[1])>1){
	$magicNumber=rand(0,9).rand(0,9).rand(0,9).rand(0,9).rand(0,9).rand(0,9).rand(0,9).rand(0,9).rand(0,9).rand(0,9);

//no sqli here either
	if (preg_match("/^[a-zA-Z]{1,}$/", $tmp[1], $matches)){$tmp[1]=$matches[0];}
	$newFileName=$magicNumber.".".$tmp[1];
	list($width, $height, $type, $attr) = getimagesize($_FILES["uploadedfile"]["tmp_name"]);
	if($width<=50 && $height<=50){
		move_uploaded_file($_FILES['uploadedfile']['tmp_name'], "uploads/".$newFileName);
		mysqli_query($con,"insert into images(name,mid) values('".$newFileName."','".$mid."')");
	}
}
header("Location: messages.php");

}
