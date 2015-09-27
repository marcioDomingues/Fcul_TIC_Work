<?php 
	if(isset($_SESSION['idAluno'])){
		session_start();
	}
	else{
		//redirect to 404 not found or smething
	}

	if(isset($_GET['calendar'])){
		echo $_SESSION['cal'];
		
		//for atualizating purposes
		unset($_SESSION['cal']);
	}else{
		$string = file_get_contents("php://input");
		$string=trim($string,"\"");
		 
	
		$_SESSION['cal']=str_replace("\\","",$string);
		echo $_SESSION['cal'];
	}

?>