<!DOCTYPE HTML>
<html>
<head>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../style.css" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300&subset=latin-ext,latin' rel='stylesheet' type='text/css'>
<title>Pesquisa</title>
</head>
<body>

<!-- php starting session , getting func id etc here -->

<div id="header">
	<img style="height:40px;margin-top:5px;margin-left:10px;" src="img/icon.png"/>
	<div id="logout"></div>
	<div id="logoutname">
		<a href='../logout.php'><?php echo $funcionario['nome'];?></a>
	</div>
</div>
<div id="pagewrap">
<div id="content">

<div id="pesquisa">
<form>
<input type="text" size="35" style="font-size:23px;">
<select style="font-size:23px;">
<option value="aluno">Aluno</option>
<option value="departamento">Departamento</option>
<option value="curso">Curso</option>
<option value="cadeira">Cadeira</option>
<option value="funcionario">Funcionário</option>
<option value="docente">Docente</option>
</select>
<input type="submit" value="Pesquisar" class="pesquisarbutton">
<div id="pesquisaresults"><a href="">
<div class="pesquisaRes" >nome</div>
<div class="pesquisaRes">coisa</div>
<div class="pesquisaRes">tantas coisas quanto for</div>
</a>
</div>

</form>
</div>

</div>
</div>
<div id="footer">
	SAT © 2014 
</div>
</body>
</html>