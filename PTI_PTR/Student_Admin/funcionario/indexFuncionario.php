<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Nome Funcionario</title>

</head>
<body>

	<?php
	require_once('../openconnection.php');
	require_once('functions.php');
	$mysqli= DB::get();
	
	$user=1;

	
	$funcionario=getInfoFuncionario($user); // GET idFuncionario, nome, foto
	
//GET PHOTO PATH
	$photopathFuncionario=$funcionario['foto'];


	?>

<div id="header">
	<img style="height:40px;margin-top:5px;margin-left:10px;" src="../img/icon.png"/>
	<div id="logout"></div>
	<div id="logoutname">
		<a href='../logout.php'><?php echo $funcionario['nome'];?></a>
	</div>
</div>
<div id="pagewrap">
	<div id="funcbar" >
		<a href="alunos.php"><div class="bartext">Alunos</div></a>
		<a href="cadeiras.php"><div class="bartext">Cadeiras</div></a>
		<a href="cursos.php"><div class="bartext">Cursos</div></a>
		<a href="departamentos.php"><div class="bartext">Departamentos</div></a>
	<div class="search"><input type="text" placeholder="Pesquisar"/><a href="#"><img style="height:20px;margin-bottom:-4px;margin-left:10px; width:auto;" src="../img/search.png"/></a></div>
	</div>

	
	<div id="content"  >
		Bem vindo <?php echo $funcionario['nome'];?> pode começar a trabalhar
					<a href="pesquisa.php">Pesquisa.php</a>
					<a href="funcAluno.php">FuncAluno.php</a>
		
		<div id="maincontent">
			<div id="text">
				
								
						
			</div>
		</div>
		
	</div>
<div id="editbar">

	</div>
</div>

<div id="footer">
	SAT © 2014
</div>
</body>
</html>