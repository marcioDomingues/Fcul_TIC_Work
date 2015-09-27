<?php
require_once( 'openconnection.php');

//gets permissions
function detectRole($in){//return 0 aluno, 1 funcionario, 2 docente

	$pieces=explode('@',$in);
	$domain=$pieces[1];
	$pieces=explode('.',$domain);
	$type=$pieces[0];

	if(strcmp($type,'alunos')==0){
		return 0;	
	}
	else if(strcmp($type,'fc')==0){
		return 1;
	}
	else{
		return -1;
	}
}

?>




