<?php 
	if(isset($_SESSION['idAluno'])){
		session_start();
	}
	else{
		//redirect to 404 not found or smething
	}


	if(isset($_GET['horario'])){
		echo $_SESSION['hor'];
	}else{
		$string = file_get_contents("php://input");
		$string=trim($string,"\"");
		 
	
		$_SESSION['hor']=str_replace("\\","",$string);
		echo $_SESSION['hor'];
	}

?>
