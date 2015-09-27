<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Departamento - Disciplinas - Modo Edição</title>
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
				<font class="title">Lista de Disciplinas</font>
				<table class="historicoTable">
					<tr>
						<td>Unidade Curricular</td>
						<td>Ano</td>
						<td>Semestre</td>
						<td>Tipo</td>
						<td>ECTS</td>
						<td></td>
					</tr>
					<tr>
						<td>
							<select>
								<option value="">0000 - Cadeira1</option>
							</select>
						</td>
						<td>
							<select id="ano">
								<option value="1">1º</option>
								<option value="2">2º</option>
								<option value="3">3º</option>
							</select>
						</td> 
						<td>
							<select id="sem">
								<option value="1">1º</option>
								<option value="2">2º</option>
							</select>

						</td>
						<td>
							<select id="tipo">
								<option value="1">Obr.</option>
								<option value="2">Opc.</option>
							</select>

						</td>
						<td>
							<input style="width:35px;" type="text" id="ects" class="ects"/>
						</td><!-- dinamico -->
						<td>
							<button>Remover</button>
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