<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Inscrições</title>
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
	
	//GET PHOTO PATH
	$photopath=$aluno['foto'];
	
	//GET CURSO NAME
	$idCurso=$aluno['idCurso'];
	$quecurso="SELECT nome FROM Cursos WHERE idCurso='$idCurso'";
	$result2=mysqli_query($mysqli,$quecurso) or die("Error: ".$quecurso);
	$curso=mysqli_fetch_assoc($result2);

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
		<a href="inscricaoTermos.php"><button class="inscrever">Inscrições Abertas!</button></a>
	</div>
	
	<div id="content">
		<div id="sidebar">
			<div id="imgsidebar">
				<img src="<?php echo $photopath; ?>"/>
			</div>
			<nav class="clearfix">
				<ul class="clearfix">
					<li><a href="indexAluno.php">Dados Pessoais</a></li>
					<li><a href="inscricoesAluno.php">Inscrições</a></li>
					<li><a href="horarioAluno.php">Horário</a></li>
					<li><a href="historicoAluno.php">Histórico de Disciplinas</a></li>
				</ul>
			</nav>
		</div>
		
		<div id="maincontent">
			<div id="text">
				<font class="title">Inscrições</font> 
				<!--if na altura de inscrições-->Clique no botão acima!
				<br/>
				Info sobre datas inscricoes blablabla 
			</div>
		</div>
		
	</div>
</div>

<div id="footer">
	SAT © 2014 
</div>

</body>
</html>