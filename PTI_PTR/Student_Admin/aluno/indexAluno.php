<!DOCTYPE HTML>
<html>
<head>

<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>

<title>Nome Aluno</title>
<meta charset="iso-8859-1">
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
				<font class="title">Dados Pessoais</font>
				<table id="dados">
				<?php
				echo " <tr>
						<td><b>Nome:</b></td>
						<td>".$aluno['nome'].
						"</td>
					</tr>
					<tr>
						<td><b>Género</b></td>
						<td>
					";
					if($aluno['genero']==0){//man
						echo "<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"m\" checked/>Masculino 
							<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"f\" disabled/>Feminino";
					}
					else if($aluno['genero']==1){//lady
						echo "<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"m\" disabled/>Masculino 
							<input style=\"width:auto;\" type=\"radio\" name=\"genero\" id=\"f\" checked/>Feminino";
					}
					echo "</td>
					</tr>
					<tr>
						<td><b>Data de Nascimento</b></td>
						<td>".$aluno['dataNascimento'].
						"</td>
					</tr>
					<tr>
						<td><b>Tipo de Documento de Identificação:</b></td>
						<td>".$aluno['tipoDoc'].
						"</td>
					</tr>
					<tr>
						<td><b>Número de Identificação:</b></td>
						<td>".$aluno['NumeroId'].
						"</td>
					</tr>
					
					<tr>
						<td><b>Morada:</b></td>
						<td>".$aluno['morada'].
						"</td>
					</tr>
					
					<tr>
						<td><b>Telefone:</b></td>
						<td>".$aluno['telefone'].
						"</td>
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
						<td>".$aluno['estatuto'].
						"</td>
					</tr>
				</table>";
						
				mysqli_free_result($result2);
				mysqli_close($mysqli);?>

			</div>
		</div>
	</div>
</div>
<div id="footer">
	SAT © 2014 
</div>
	
</body>
</html>
