<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Nome Aluno - Definições de Conta</title>
</head>
<body>

<!-- php starting session , getting func id etc here -->

<div id="header">
	<img style="height:40px;margin-top:5px;margin-left:10px;" src="img/icon.png"/>
	<div id="logout"></div>
	<div id="logoutname">
		<a href='../logout.php'><?php echo $funcionario['nome'];?></a>
	</div>
</div>
<div id="pagewrap">
	<div id="topbar">
		<a href="defAlunoEdit.php"><button class="edit">Modificar Dados</button></a>
	</div>
	<div id="content">
		<div id="sidebar">
			<div id="imgsidebar">
				<!-- GET IMG FROM DATABASE -->
				<img src="img/college-girl.jpg"/>
			</div>
			<nav class="clearfix">
				<ul class="clearfix">
					<li><a href="indexAluno.php">Dados Pessoais</a></li>
					<li><a href="horarioAluno.php">Horário</a></li>
					<li><a href="historicoAluno.php">Histórico de Disciplinas</a></li>
					<li><a href="inscricoesAluno.php">Inscrições</a></li>
					<li><a href="definicoesAluno.php">Definições de Conta</a></li>
				</ul>
			</nav>
		</div>
		
		<div id="maincontent">
			<div id="text">
				<font class="title">Definições de Conta</font> 
				<br/>
				<!-- coisas pre-preenchidas. mudanca password? podemos tirar esta pagina prolly lel -->
				Opções, dropdowns, ..
			</div>
		</div>
	</div>
</div>
<div id="footer">
	SAT © 2014 
</div>
</body>
</html>