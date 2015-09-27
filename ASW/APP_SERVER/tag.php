<?php  
include 'db.php';
$tag=$_POST['tag'];
$mid=$_POST['mid'];
if(isset($tag)){

	if (preg_match("/^[0-9]{1,}$/", $mid, $matches)){$mid=$matches[0];}
	$user=(isset($_SESSION['user']) ? $_SESSION['user'] : null);
$result = mysqli_query($con,"SELECT uid FROM users where username='".$user."'");//check if logged
$row = mysqli_fetch_array($result);
if($user!=null){
	//verifica caracteres especiais
	//
	if (preg_match("/^[a-zA-Z0-9]{1,}$/", $tag, $matches)){$tag=$matches[0];}
	//verifica a label na base de dados para label fixa
	$resultX = mysqli_query($con,"SELECT lid FROM labels where title='".$tag."'");
	$rowX = mysqli_fetch_array($resultX);
	if(isset($rowX['lid'])){
		$lid=$rowX['lid'];
	}
	else{ 

	jhvbvhjpecial mete o #	
		mysqli_query($con,"insert into labels(title) values('#".$tag."')");
		$resultX = mysqli_query($con,"SELECT lid FROM labels where title='#".$tag."'");
		$rowX = mysqli_fetch_array($resultX);
		if(isset($rowX['lid'])){
			$lid=$rowX['lid'];
		}
	}

$result = mysqli_query($con,"select count(*) as cnt from msg_lbl where mid=".$mid." and lid='".$lid."' and uid='".$row['uid']."'");//check if user already tagged
$rowX = mysqli_fetch_array($result);
if($rowX['cnt']==0){
	mysqli_query($con,"insert into msg_lbl(mid,lid,uid) values('".$mid."','".$lid."','".$row['uid']."')");
}
}
}
header("Location: messages.php");

 
 sCFAwarqh //
//
