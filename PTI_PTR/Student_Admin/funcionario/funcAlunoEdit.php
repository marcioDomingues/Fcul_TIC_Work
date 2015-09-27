<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
 <link rel='stylesheet' type='text/css' href='../libs/css/smoothness/jquery-ui-1.8rc3.custom.css' />
<script type='text/javascript' src='../libs/jquery-ui.min.js'></script>
    <script type='text/javascript' src='../libs/jquery-1.4.2.min.js'></script>

<!--qeisto, download it, no online links pls-->
 <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">


 <script>$(function() {
  					$( "#datepicker" ).datepicker({dateFormat: "yy-mm-dd"});});
  					</script>
<title>Nome Aluno - Modo Edição</title>
</head>
<body>
	<?php
	require_once('../openconnection.php');
	require_once('functions.php');
	$mysqli= DB::get();
	
	//COOKIE get user, permission, blabla
	//if(isset($_COOKIE["alunoid"]))
		//$userAluno=idAluno;

	$userAluno=1;

	//COOKIE para o funcionario
	$user = 1;
	
	$aluno=getInfoAluno($userAluno);
	$funcionario=getInfoFuncionario($user); // GET idFuncionario, nome, foto
	
//GET PHOTO PATH
	$photopath=$aluno['foto'];
	$photopathFuncionario=$funcionario['foto'];

	//GET CURSO NAME
	$idCurso=$aluno['idCurso'];
	$quecurso="SELECT nome FROM Cursos WHERE idCurso='$idCurso'";
	$result2=mysqli_query($mysqli,$quecurso) or die("Error: ".$quecurso);
	$curso=mysqli_fetch_assoc($result2);

	?>
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
					<li><a href="funcAlunoHor.html">Horário</a></li>
					<li><a href="funcAlunoHis.html">Histórico de Disciplinas</a></li>
					<li><a href="funcAlunoIns.html">Inscrições</a></li>
					<li><a href="funcAlunoDef.html">Definições de Conta</a></li>
				</ul>
			</nav>
		</div>
		<div id="maincontent">
			<div id="text">
				<font class="title">Dados Pessoais</font>
				<table id="dados">
					<?php
				echo " <tr>
						<td><b>Nome:</b></td>
						<td>
							<input type=\"text\" id=\"nome\" class=\"nome\" value=\"".$aluno['nome'].
						"\"/>
						</td>
					</tr>
					<tr>
						<td><b>Género</b></td>
						<td>
					";
					if($aluno['genero']==0){//man
						echo "<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"m\" checked/>Masculino 
							<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"f\" />Feminino";
					}
					else if($aluno['genero']==1){//lady
						echo "<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"m\" />Masculino 
							<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"f\" checked/>Feminino";
					}
					echo "</td>
					</tr>
					<tr>
						<td><b>Data de Nascimento</b></td>
						<td>
						<input type=\"text\" id=\"datepicker\" value=\"".$aluno['dataNascimento']."\" >
						</td>
					</tr>
					<tr>
						<td><b>Tipo de Documento de Identificação:</b></td>
						<td><select style=\"width:auto;\" name=\"tipoid\" id=\"tipoid\">";
						if($aluno['tipoDoc'].strcmp('Cartão de Cidadão')==0){
								echo "<option value=\"bi\">Bilhete de Identidade</option>
								<option selected value=\"cc\">Cartão de Cidadão</option>";
						}
					
						else if($aluno['tipoDoc'].strcmp('Bilhete de Identidade')==0){
								echo "<option selected value=\"bi\">Bilhete de Identidade</option>
								<option  value=\"cc\">Cartão de Cidadão</option>";
							}

							echo "</select></td>
					</tr>
					<tr>
						<td><b>Número de Identificação:</b></td>
						<td>
							<input type=\"number\" name=\"docid\" id=\"docid\" value=\"".$aluno['NumeroId']."\"/> 
						</td>
					</tr>
					
					<tr>
						<td><b>Morada:</b></td>
						<td>
							<input type=\"text\" name=\"morada\" id=\"morada\" value=\"".$aluno['morada']."\"/>
						</td>
					</tr>
					
					<tr>
						<td><b>Telefone:</b></td>
						<td>
							<input type=\"text\" name=\"tlf\" id=\"tlf\" value=\"".$aluno['telefone']."\" />
					</tr>
					
				</table>";?>
				
				<br/><br/><font class="title">Informação Académica</font>
				<table id="dados">
					<tr>
						<td><b>Curso</b></td>
						<?php 
						echo "<td>".$curso['nome'].
						"</tr>
					<tr>
						<td><b>Número de Aluno</b></td>
						<td>".$aluno['idAluno'].
						"</td>
					</tr>
					<tr>
						<td><b>Data de Matrícula</b></td>
						<td>".$aluno['dataIngresso'].
						"</td>
					</tr>
					<tr>
						<td><b>Último Ingresso</b></td>
						<td>".$aluno['dataUltimaMatricula'].
						"</td>
					</tr>
					<tr>
						<td><b>Estatuto Aluno</b></td>
						<td>";
						if($aluno['estatuto']==1){
							echo "<select style=\"width:auto;\" name=\"estatuto\" id=\"estatuto\">
							<option selected value=\"1\">Normal</option>
							<option value=\"2\">Trabalhador-Estudante</option> 
						</select>";
					}
						else if($aluno['estatuto']==2){
							echo "<select style=\"width:auto;\" name=\"estatuto\" id=\"estatuto\">
							<option value=\"1\">Normal</option>
							<option selected value=\"2\">Trabalhador-Estudante</option> 
						</select>";
					}


						echo "</td>
					</tr>
				</table>";
						
				//mysqli_free_result($result);
				mysqli_free_result($result2);
				mysqli_close($mysqli);?>

				<br/><button class="submit" type="submit">Guardar Dados</button>
			</div>
		</div>
	</div>
</div>
<div id="footer">
	SAT © 2014
</div>
</body>
</html>