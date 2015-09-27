<?php 

	require_once( '../openconnection.php');
	require_once( 'functions.php');
session_start();
	if(isset($_SESSION['idAluno'])){
		
		$user=$_SESSION['idAluno'];
		$mysqli= DB::get();
	}
	else{
		//redirect to 404 not found or smething
	}

	$semestre=1;

	$aluno=getInfoAluno($user);
	
	//GET PHOTO PATH
	$photopath=$aluno['foto'];

	$horario=getHorario($user);
?>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>

<link rel='stylesheet' type='text/css' href='calendar/reset.css' />


    <link rel='stylesheet' type='text/css' href='../libs/css/smoothness/jquery-ui-1.8rc3.custom.css' />

	<link rel='stylesheet' type='text/css' href='calendar/jquery.weekcalendar.css' />
	<link rel='stylesheet' type='text/css' href='calendar/demo.css' />

	<script type='text/javascript' src='../libs/jquery-ui.min.js'></script>
    <script type='text/javascript' src='../libs/jquery-1.4.2.min.js'></script>
    
    <script type='text/javascript' src='../libs/jquery-ui-1.8rc3.custom.min.js'></script>
	<script type='text/javascript' src='calendar/jquery.weekcalendar.js'></script>
	<script type='text/javascript' src='horario.js'></script>

<title>Horário</title>

</head>
<?php echo "<body onload='initHorario(\"". $horario ."\");'>";?>
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
		
			
			<!-- HORARIO -->
			<font class="title" style="font-size:13pt;">Ano Lectivo - Semestre - Curso</font>
			<br/><br/>
			
			<div id='calendar'></div>
			</div>
		</div>
	</div>
</div>
<div id="footer">
	SAT © 2014 
</div>
</body>
</html>
