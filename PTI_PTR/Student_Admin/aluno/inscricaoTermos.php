<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Termos e Condi��es</title>
</head>
<body>
<?php
	require_once('../openconnection.php');
	require_once('functions.php');
	
	session_start();
	if(isset($_SESSION['idAluno'])){
		
		$user=$_SESSION['idAluno'];
		$mysqli= DB::get();
	}
	else{
		//redirect to 404 not found or smething
	}
		
	$aluno=getInfoAluno($user);

?>

<div id="header">
	<img style="height:40px;margin-top:5px;margin-left:10px;" src="img/icon.png"/>
	<div id="logout"></div>
	<div id="logoutname">
		<a href='../logout.php'><?php echo $aluno['nome'];?></a>
	</div>
</div>
<div id="pagewrap">
	<div id="topbar">
	<font class="title">Termos e Condi��es</font>
	</div>
	<div style="text-align:center;" id="content">
		<div id="termos">
			Termos e Condi��es ..
		</div>
		<br/>
		<!-- onclick function para mudar bd aluno[termosCondicoes] -->
		<button style="width:auto;" class="filbutton"> Li e aceito os termos e as condi��es > </button>
	</div>
</div>
<div id="footer">
	SAT � 2014 
</div>
</body>
</html>