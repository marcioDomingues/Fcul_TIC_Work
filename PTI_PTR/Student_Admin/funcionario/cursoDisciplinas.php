<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Curso - Disciplinas</title>
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
		<a href="cursoDisciplinasEdit.php"><button class="edit">Modificar Dados</button></a>
	</div>
	<div id="content">
		<div id="sidebar">
			<div id="imgsidebar">
				<!-- GET IMG FROM DATABASE -->
				<img src="img/curso.jpg"/>
			</div>
			<nav class="clearfix">
				<ul class="clearfix">
					<li><a href="indexCurso.php">Geral</a></li>
					<li><a href="cursoDisciplinas.php">Lista de Disciplinas</a></li>
				</ul>
			</nav>
		</div>
		<div id="maincontent">
			<div id="text">
				<font class="title">Lista de Disciplinas</font>
				<table class="historicoTable">
					<tr>
						<td>Código</td>
						<td>Unidade Curricular</td>
						<td>Ano</td>
						<td>Semestre</td>
						<td>Tipo</td>
						<td>ECTS</td>
					</tr>
					<tr>
						<td>000000</td>
						<td>Cadeira1</td>
						<td>1º</td>
						<td>1º</td>
						<td>Obr.</td>
						<td>6</td>
					</tr>
					<tr>
						<td>000001</td>
						<td>Cadeira2</td>
						<td>1º</td>
						<td>2º</td>
						<td>Obr.</td>
						<td>6</td>
					</tr>
					<tr>
						<td>000002</td>
						<td>Cadeira3</td>
						<td>2º</td>
						<td>1º</td>
						<td>Obr.</td>
						<td>6</td>
					</tr>
					<tr>
						<td>000003</td>
						<td>Cadeira4</td>
						<td>2º</td>
						<td>2º</td>
						<td>Obr.</td>
						<td>6</td>
					</tr>
					<tr>
						<td>000004</td>
						<td>Cadeira5</td>
						<td>3º</td>
						<td>1º</td>
						<td>Obr.</td>
						<td>6</td>
					</tr>
					<tr>
						<td>000005</td>
						<td>Cadeira6</td>
						<td>3º</td>
						<td>2º</td>
						<td>Obr.</td>
						<td>6</td>
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