<!DOCTYPE HTML>
<html>
<head>
	<meta charset="iso-8859-1">
	<link rel="stylesheet" href="../style.css" type="text/css">
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
	<title>Histórico de Disciplinas</title>
</head>
<body>

	<?php
		error_reporting(E_ALL);
		ini_set('display_erros','1');

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
		$aluno = getInfoAluno($user);

		if (isset($_POST['filtrar'])) {
		
			//ano lectivo reg exp
			if ( preg_match('%^[0-9._\%-]{9}$%', stripslashes( trim( $_POST['Ano_Lectivo'] ) ) ) )
			{
				$ano_Lectivo = sanitize_escape_data( $_POST['Ano_Lectivo']) ;
				
			} else {
				$ano_Lectivo = NULL;
				
			}
			//semestre reg exp
			if ( preg_match('%^[(1)(2)]$%', stripslashes( trim( $_POST['Semestre'] ) ) ) )
			{
				$semestre = sanitize_escape_data( $_POST['Semestre']) ;
				
			} else {
				$semestre = NULL;
				
			}
			//estado reg exp
			if ( preg_match('%([\w ]+)%', stripslashes( trim( $_POST['Estado'] ) ) ) )
			{
				$estado = sanitize_escape_data( $_POST['Estado']) ;
				
			} else {
				$estado = NULL;
				
			}
		}	


		

		$query = "SELECT idDisciplina, nota, estado, anoLectivo 
				  FROM DisciplinasAlunos 
				  WHERE idAluno = '$user'";
		
		$result=$mysqli->query($query) or die("Error: ".$query);

		//////////////////////////////////////////
		//add result to $resultsArray
		while($lines = $result->fetch_assoc() ){
			$resultsArray[] = $lines;
		}
		
		//////////////////////////////////////////
		//for each entry in $resultArray add result of second query to $resultsArray
		foreach ($resultsArray as $value)
		{
			$idDisciplina=$value['idDisciplina'];
			$query2 = "SELECT nome, ects, ano, semestre 
					   FROM Disciplinas 
					   WHERE idDisciplinas='$idDisciplina'";

			$result2=$mysqli->query($query2) or die("Error: ".$query2);  
			
			$lines = $result2->fetch_assoc();

			$newResultsArray[] = array_merge($value, $lines);
		}


		//////////////////////////////////////////
		// Sort the data with 'ano' ascending, 'semestre' ascending
		//array_orderby($ano, SORT_ASC, $semestre, SORT_ASC);
		$newResultsArray = array_orderby($newResultsArray, 'ano', SORT_ASC, 'semestre', SORT_ASC);


		//////////////////////////////////////////
		//transfer $newResultsArray  to $array2
		//this is the one to show
		//apply filter if variables are set
		$array2 = $newResultsArray;

		if ( isset( $ano_Lectivo ) ) 
		{
			//echo $ano_Lectivo . "<br>";
			$array2 = removeElementsWithoutValue($array2, 'anoLectivo', $ano_Lectivo);

		}
		if ( isset( $semestre ) ) 
		{
			//echo $semestre . "<br>";
			$array2 = removeElementsWithoutValue($array2, 'semestre', $semestre);

		}
		if ( isset( $estado ) ) 
		{
			//echo $estado . "<br>";
			$array2 = removeElementsWithoutValue($array2, 'estado', $estado);

		}
	
		//////////////////////////////////////////
		///add to functions
		///
		//////////////////////////////////////////


		//////////////////////////////////////////
		///order array associative by key
		//Add $data as the last parameter, to sort by the common key
		//array_orderby($ano, SORT_ASC, $semestre, SORT_ASC, $newResultsArray);
		///example: 
		///$newResultsArray = array_orderby($newResultsArray, 'ano', SORT_ASC, 'semestre', SORT_ASC);
		//////////////////////////////////////////
		function array_orderby()
		{
			$args = func_get_args();

			$data = array_shift($args);
			foreach ($args as $n => $field) {
				if (is_string($field)) {
					$tmp = array();
					foreach ($data as $key => $row)
						$tmp[$key] = $row[$field];
					$args[$n] = $tmp;
				}
			}
			$args[] = &$data;
			call_user_func_array('array_multisort', $args);
			return array_pop($args);
		}

		
		//////////////////////////////////////////
		///remove all elements from associative 
		///where key dont match the value inputed
		///example:
		///$array = removeElementWithValue($array, 'key', value);
		//////////////////////////////////////////
		function removeElementsWithoutValue($array, $key, $value)
		{
     		foreach($array as $subKey => $subArray)
     		{
          		if($subArray[$key] != $value){
               		unset($array[$subKey]);
          		}
     		}
     		return $array;
		}

		//////////////////////////////////////////
		/// A function that strips harmful data.
		//////////////////////////////////////////
		function sanitize_escape_data ($data) 
		{
			// Check for mysql_real_escape_string() support.
			// This function escapes characters that could be used for sql injection
			if (function_exists('mysql_real_escape_string')) 
			{
				global $mysqli; // Need the connection.
				$data = $mysqli->real_escape_string( trim($data) );
				$data = strip_tags($data);
			} else {
				$data = mysql_escape_string ( trim($data) );
				$data = strip_tags($data);
			}
			// Return the escaped value.	
			return $data;
		} // End of function.

		//////////////////////////////////////////
		///add to functions
		///
		//////////////////////////////////////////
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
					<img src=<?php echo $aluno['foto']; ?>>
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
					<font class="title">Histórico de Disciplinas</font>
					<br/>
					
					<!-- FILTROS -->

	<form action="http://localhost/~MDo/test/v8/historicoAluno.php" method="post" id="submition">
			
			<table id="filtros">
						<tr>
							<td>
								<select name="Ano_Lectivo" form="submition">
									<!-- preencher isto d AL atual ate AL data matricula -->
									
									<!-- -->
								 	<?php
										if ( isset( $ano_Lectivo ) ) 
										{	
											//echo "<option selected=\"selected\" disabled=\"disabled\">" . $ano_Lectivo . "</option>";
											echo "<option selected=\"true\" style=\"display:none;\">" . $ano_Lectivo . "</option>";
										}
									?>
									<!-- -->
									<option value="">Ano Lectivo</option>
									<option value="2011-2012">2011-2012</option>
									<option value="2012-2013">2012-2013</option>
									<option value="2013-2014">2013-2014</option>
									
								</select>
							</td>
							<td>
								<select name="Semestre" form="submition">
									<?php
										if ( isset( $semestre ) ) 
										{	
											echo "<option selected=\"true\" style=\"display:none;\">" . $semestre . "</option>";
										}
									?>
									<option value="">Semestre</option>
									<option value="1">1º Semestre</option>
									<option value="2">2º Semestre</option>
								</select>
							</td>
							<td>
								<select name="Estado" form="submition">
									<?php
										if ( isset( $estado ) ) 
										{	
											echo "<option selected=\"true\" style=\"display:none;\">" . $estado . "</option>";
										}
									?>
									<option value="">Estado</option>
									<option value="Inscrito">Inscrito</option>
									<option value="Aprovado">Aprovado</option>
									<option value="Reprovado">Reprovado</option>
									<option value="Não Avaliado">Não Avaliado</option>
								</select>
							</td>
							<td>
								<input type="submit" name="filtrar" value="Send" id="indexloginbutton" />
							</td>
						</tr>
					</table>
			
			
	</form>

					<!-- TABELA(S) -->
					<br/>
					<font class="title" style="font-size:13pt;">
						<?php
						if ( isset( $ano_Lectivo ) ) 
						{
							echo $ano_Lectivo . " - ";
						}else{
							echo "Ano Lectivo - ";
						}
						if ( isset( $semestre ) ) 
						{
							echo $semestre . "˚ Semestre - ";
							
						}else{
							echo "Semestre - ";
						}


						?>
						 <!-- ir buscar as sessions -->
						Curso
					</font>

					<table class="historicoTable">
						<!-- primeira coluna -->
						<tr>
							<td>Disciplina</td>
							<td>Ano</td>
							<td>Semestre</td>
							<td>Classificação</td>
							<td>ECTS</td>
							<td>Tipo</td>
							<td>Estado</td>
							
						</tr>

						<!-- a partir daqui inserir dados conforme filtros -->
						<?php
							foreach ($array2 as $value)
							{
	  							//Disciplina 	Classificação 	ECTS 	Tipo 	Estado	
	  							//Array ( [idDisciplina] => 1 [nota] => 15 [estado] => 1 [nome] => Programa��o I [ects] => 6 ) 
								echo "<tr>";
								echo "<td>".$value ['nome']."</td>";
								echo "<td>".$value ['ano']."</td>";
								echo "<td>".$value ['semestre']."</td>";
								echo "<td>".$value ['nota']."</td>";
								echo "<td>".$value ['ects']."</td>";
								echo "<td>CONTENT</td>";
								echo "<td>".$value ['estado']."</td>";								
								echo "</tr>";
							}
						
						?>

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