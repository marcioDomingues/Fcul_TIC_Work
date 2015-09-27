<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Definições de Conta do Aluno</title>
</head>
<body>
<!-- php starting session , getting func id etc here -->

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
	
	
	<div id="content">
		<div id="sidebar">
			<div id="imgsidebar">
				<!-- GET IMG FROM DATABASE -->
				<img src="img/college-girl.jpg"/>
			</div>
			<nav class="clearfix">
				<ul class="clearfix">
					<li><a href="funcAluno.php">Dados Pessoais</a></li>
					<li><a href="funcAlunoHor.php">Horário</a></li>
					<li><a href="funcAlunoHis.php">Histórico de Disciplinas</a></li>
					<li><a href="funcAlunoIns.php">Inscrições</a></li>
					<li><a href="funcAlunoDef.php">Definições de Conta</a></li>
				</ul>
			</nav>
		</div>
		
		<div id="maincontent">
			<div id="text">
				<font class="title">Definições de Conta do Aluno</font>
				<br/>
				
				lol cenas
			</div>
		</div>
	</div>
</div>
<div id="footer">
	SAT © 2014
</div>
</body>
</html>