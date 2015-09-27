<?php
	// Initialize a session.
    include_once 'openconnection.php';
    include_once 'functions.php';
	session_unset();
	session_start();


      if (isset($_POST['submit'])) { // Check if the form has been submitted.
		echo "submitted";

		if(preg_match('%^[a-z0-9.]{2,20}+@[a-z.]+\.(ul)\.(pt)%',stripslashes(trim($_POST['user'])))){
	
			$u = escape_data($_POST['user']);
			
		} else {

			$u = FALSE;

			echo '<p><font color="red" size="+1">Please enter a valid User ID!</font></p>';

		}
		
		// FIX IT Check for a good password

		if (preg_match ('%^[A-Za-z0-9]\S{6,20}$%', stripslashes(trim($_POST['pass'])))) {

			$p = escape_data($_POST['pass']);
			

		} else {

			$p = FALSE;
			
			echo '<p><font color="red" size="+1">Please enter a valid Password!</font></p>';

		}
	}

		// Query the database.
	
		if ($u && $p ) {
		
			$role=detectRole($u);

			if($role==0){//aluno
				$query = "SELECT idAluno,idCurso,idMinor FROM Alunos WHERE email='$u' AND password='$p'";	//SHA	
			}
			//funcionario
			else if($role==1){
				$query = "SELECT idFuncionario FROM Funcionarios WHERE email='$u' AND password='$p'";
			}

			$mysqli= DB::get();
			
			$result = $mysqli->query ($query);

			if ($mysqli->affected_rows == 1) { // A match was made; login successful
			$dados=$result->fetch_assoc();
			$tokenId = rand(10000, 9999999);


				if($role==0){				//aluno
				
					$_SESSION['idAluno'] = $dados['idAluno'];
				
					if(isset($dados['idCurso'])){
						$_SESSION['idCurso'] = $dados['idCurso'];
					}
					else{
						echo "Error creating session";
						$mysqli->close(); // Close the database connection.
						exit();
					}

					if(isset($dados['idMinor'])){
						$_SESSION['idMinor'] = $dados['idMinor'];
					}

					//o erro era aqui, a gaja n é obrigada a ter minor fds
					/*else{
						echo "Error creating session";
						$mysqli->close(); // Close the database connection.
						exit();
					}*/

					$query2 = "UPDATE Alunos SET tokenId = ".$tokenId." WHERE idAluno= ".$_SESSION['idAluno'];

				}
				
				
				//funcionario
				else if($role==1){
					$_SESSION['idFuncionario'] = $dados['idFuncionario'];

					$query2 = "UPDATE Funcionarios SET tokenId = ".$tokenId." WHERE idFuncionario= ".$_SESSION['idFuncionario'];
				}

				//error
				else{
					echo "Error registring session";
					$mysqli->close(); // Close the database connection.
					exit();
				}
				

				$result2 = $mysqli->query ($query2);
		
				$_SESSION['token_id'] = $tokenId;
				
				session_regenerate_id();

				//aluno
				if($role==0){
					header("Location: aluno/indexAluno.php");
				}
				//funcionario
				else if($role==1){
					header("Location: funcionario/indexFuncionario.php");
				}
				
				
				$mysqli->close(); // Close the database connection.
				exit();
			}

			else { // No match was made; login fail

				echo '<br><br><p><font color="red" size="+1">Either the Userid or Password are incorrect</font></p>'; 
				$mysqli->close(); // Close the database connection
				exit();
			}

			echo '<br><br><p><font color="red" size="+1">Either the Userid or Password are incorrect</font></p>'; 
			$mysqli->close(); // Close the database connection
			exit();
		}

// End of SUBMIT conditional.*/

?>
    
