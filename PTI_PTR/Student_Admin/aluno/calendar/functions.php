<?php
include_once( '../../openconnection.php');

function getInfoAluno($idAluno){
	$mysqli=DB::get();
	$query = "SELECT idAluno, nome, foto, dataIngresso, dataUltimaMatricula, matriculas,estado, estatuto, idCurso, idMinor, tipoDoc, NumeroId,genero,dataNascimento,morada,telefone FROM Alunos WHERE idAluno='$idAluno'";
	$result=mysqli_query($mysqli,$query) or die("Error: ".$query);
	
	return mysqli_fetch_array($result);
}


function getAnoLectivo($aluno){
$mysqli=DB::get();
	$que = "SELECT matriculas, idCurso FROM Alunos WHERE idAluno = '$aluno'";
	$result=mysqli_query($mysqli,$que) or die("Error: ".$que);

	
	$dados=mysqli_fetch_assoc($result);
	$matriculas=$dados["matriculas"];
	$curso=$dados["idCurso"];
	
	$que = "SELECT duracao FROM Cursos WHERE idCurso = '$curso'";
	$result=mysqli_query($mysqli,$que) or die("Error: ".$que);
	
	
	$dados=mysqli_fetch_assoc($result);
	$duracao=$dados["duracao"];
	
	
	$matriculas++;
	
	if($matriculas>$duracao){
		$matriculas=$duracao;
	}

	return $matriculas;
}


//gets all from sem and ano
function loadCadeiras($ano, $curso, $semestre){

	echo "sdfssf".$ano."-".$curso."-".$semestre;
	$mysqli=DB::get();
$que="SELECT t.idTurmas,d.idDisciplinas,d.nome,t.turma,t.vagas,d.ects,d.ano,h.diaSemana,h.inicio,h.fim,s.Numero as sala,do.nome as docente
 FROM DisciplinasCursos dc, Disciplinas d, Turmas t, Horarios h, Salas s, Docentes do WHERE t.idDisciplina=d.idDisciplinas 
 AND dc.idCurso='$curso' AND dc.idDisciplina=d.idDisciplinas AND d.ano ='$ano' AND d.semestre='$semestre' AND s.idSalas=t.idSala AND do.idDocente=t.idDocente 
 AND h.idTurma=t.idTurmas";

	$result=mysqli_query($mysqli,$que) or die("Error: ".$que);
	return $result;
	
	}

function prepareString($result){
$string="";
	if(mysqli_num_rows($result)>0){
		
	while($row=mysqli_fetch_assoc($result)){
		
		$string .= 
		  $row['idTurmas'] . ","
		. $row['idDisciplinas'] . ","
		. $row['nome'].","
		. $row['turma'].","
		. $row['vagas'].","
		. $row['ects'].","
		. $row['ano'].","
		. $row['diaSemana'].","
		. $row['inicio'].","
		. $row['fim'].","
		. $row['sala'].","
		.$row['docente'].";";
		
	}}

return rtrim($string,";");

}


function getHorario($aluno){ 
	$mysqli=DB::get(); 
	$que="SELECT ta.idTurma AS idTurmas, ta.idAluno, t.turma, t.idSala, 
	t.idDisciplina AS idDisciplinas, t.idDocente, idHorario, h.idTurma, 
	h.inicio AS start, h.fim AS end, h.diaSemana, d.nome AS title, d.ects, 
	d.ano, t.vagas, s.Numero AS sala, do.nome AS docente FROM TurmasAlunos ta, 
	Turmas t, Horarios h, Disciplinas d, Salas s, Docentes do WHERE idAluno = $aluno 
	AND t.idTurmas = ta.idTurma AND h.idTurma = t.idTurmas AND 
	d.idDisciplinas = t.idDisciplina AND s.idSalas = t.idSala AND
	 do.idDocente = t.idDocente"; $result=mysqli_query($mysqli,$que) or die("Error: ".$que); 
return prepareString($result); 
}


?>




