<?php 

	include( '../../openconnection.php');
	include( 'functions.php');
	
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


	$ano=getAnoLectivo($user);
	
	$curso=$aluno['idCurso'];

//get cadeiras, send to js
$result=loadCadeiras($ano,$curso,$semestre);

$sendCadeiras=prepareString($result);	
if(mysqli_data_seek($result,0)){
	$aux=$result;
	echo "went to 0";
}

?>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>


	<link rel='stylesheet' type='text/css' href='reset.css' />

    <link rel='stylesheet' type='text/css' href='../../libs/css/smoothness/jquery-ui-1.8rc3.custom.css' />


	<link rel='stylesheet' type='text/css' href='jquery.weekcalendar.css' />
	<link rel='stylesheet' type='text/css' href='demo.css' />


<script type='text/javascript' src='../../libs/jquery-ui.min.js'></script>
    <script type='text/javascript' src='../../libs/jquery-1.4.2.min.js'></script>
    
    <script type='text/javascript' src='../../libs/jquery-ui-1.8rc3.custom.min.js'></script>
	<script type='text/javascript' src='jquery.weekcalendar.js'></script>
	<script type='text/javascript' src='demo.js'></script>
	<script>
	addCadeiras(<?php echo $sendCadeiras; ?>);
	</script>
<title>Horário</title>
</head>
<body>
<div id="header">
	<img style="height:40px;margin-top:5px;margin-left:10px;" src="../img/icon.png"/>
	<div id="logout"></div>
	<div id="logoutname">
		<a href='../logout.php'><?php echo $aluno['nome'];?></a>
	</div>
</div>
<div id="pagewrap">
<div id="content">

<div style="text-align:center;" class="title">Escolha de Horário/Turma</div>
<div id="horarioalunoContainer">

	<div id="horarioalunoL">
	
<div id="calendar"></div>

</div>
	<div id="horarioalunoR">
	<div><input id="horarioalunopesquisa" type="text" placeholder="Pesquisar cadeiras"><button class="search">Pesquisar</button></div>
	<table id="filtros">
					<tr>
						<td>
							<select>
								<!-- preencher isto d AL atual ate AL data matricula -->
								<option>Ano Lectivo</option>
								<option>1º</option>
								<option>2º</option>
								<option>3º</option>
								<option>Opc</option>
							</select>
						</td>
						<td>
							<select>
								<option>Vagas</option>
								<option>Com vagas</option>
								<option>Sem vagas</option>
							</select>
						</td>
						<td>
							<button class="filtrar">Filtrar</button>
						</td>
					</tr>
				</table>
	
		<div id="horarioalunosRinner">

			<div id="horarioalunoREcts">ECTS INSCRITOS: 160</div>
			
			<div id="horarioalunosRoptions">
				<div>
					<?php
					//fix for more teoricas
				if(mysqli_num_rows($result)>0){

					while($row=mysqli_fetch_assoc($result)){
						if($row['turma']>9&&$row['turma']<20){
							echo "---".$row['nome']."---";
							echo "<button id=\"".$row['idTurmas']."\" class=\"add\" style=\"display:block;\" onclick=\"choose(".$row['idTurmas'].")\">Adicionar</button>
							<button id=\"R".$row['idTurmas']."\" class=\"remove\" style=\"display:block;\" onclick=\"removeC(".$row['idTurmas'].")\">Remover</button>";
							

							//ignore this part for now
							while($cad=mysqli_fetch_assoc($aux)){
								if($cad['idDisciplinas']==$row['idDisciplinas'] && $cad['turma']>19){
									echo "<option>TP".$cad['turma']."</option>";
								}
							}
						}
					}
				}

				?>
			</br>

				</div>
			</div>
			</div>
		</div>
		
		<button onclick="">Finalizar</button>
		
	</div>
</div>
</div>
<div id="footer">
	SAT © 2014 
</div>
</body>
</html>
