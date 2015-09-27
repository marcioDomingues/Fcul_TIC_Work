<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Departamento - Cursos</title>
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
		pesquisa etc
	</div>
	<div id="topbar" style="text-align:right;">
		<a href="depCursosEdit.php"><button class="edit">Modificar Dados</button></a>
	</div>
	<div id="content">
		<div id="sidebar">
			<div id="imgsidebar">
				<!-- GET IMG FROM DATABASE -->
				<img src="img/banner-DI.jpg"/>
			</div>
			<nav class="clearfix">
				<ul class="clearfix">
					<li><a href="indexDepartamento.php">Geral</a></li>
					<li><a href="depCursos.php">Lista de Cursos</a></li>
					<li><a href="depDisciplinas.php">Lista de Disciplinas</a></li>
				</ul>
			</nav>
		</div>
		
		<div id="maincontent">
			<div id="text">
				<font class="title">Lista de Cursos</font>
				<!-- editar css do historicoTable pra adaptar -->
				<table class="historicoTable">
					<tr>
						<td>Código</td>
						<td>Curso</td>
						<td>Grau</td>
						<td>ECTS</td>
					</tr>
					<tr>
						<td>000000</td>
						<td>Curso1</td>
						<td>Licenciatura</td>
						<td>180</td>
					</tr>
					
				</table>

			</div>
		</div>
	</div>
</div>
<div id="footer">
	SAT © 2014 
</div>
</body>
</html>