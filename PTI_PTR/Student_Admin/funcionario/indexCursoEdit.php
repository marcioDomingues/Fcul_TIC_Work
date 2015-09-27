<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Curso - Modo de Edição</title>
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
		<a href="#"><button class="edit">Guardar Modificações</button></a>
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
				<font class="title">Geral</font>
				<table id="dados">
					<tr>
						<td>
							<b>Curso:</b>
						</td>
						<td>
							<input type="text" id="cursoname" class="cursoname" />
						</td>
					</tr>
					<tr>
						<td>
							<b>Grau:</b>
						</td>
						<td>
							<select id="grau" class="grau">
								<option value="1">Minor</option>
								<option value="2">Licenciatura</option>
								<option value="3">Mestrado</option>
								<option value="4">Doutoramento</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<b>Código: </b>
						</td>
						<td>
							CONTENT
						</td>
					</tr>
					<tr>
						<td>
							<b>Departamento: </b>
						</td>
						<td>
							<select id="dep" class="dep">
								<option value="1">Departamento1</option>
								<option value="2">Departamento2</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<b>ECTS: </b>
						</td>
						<td>
							<input type="number" id="ects" class="ects" />
						</td>
					</tr>
					<tr>
						<td>
							<b>Coordenador: </b>
						</td>
						<td>
							<select id="coordenador" class="coordenador">
								<option value="1">professor qualificado 1</option>
								<option value="2">professor qualificado 2</option>
								<option value="3">professor qualificado 3</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<b>Descrição: </b>
						</td>
						<td>
							<input type="text" id="desc" class="desc" />
						</td>
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