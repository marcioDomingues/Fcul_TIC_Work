<?php
//error_reporting(0);

$db_user="asw42482";
$db_pass="asw42482";
$db_host="localhost";
$db_db="asw42482";
$con=mysqli_connect($db_host,$db_user,$db_pass,$db_db);
if(mysqli_connect_errno()){
echo "Database ERROR!";
die;
}
session_start();


//acede a base de dados mysqlconnect
//faz ligacao
//em caso de erro desliga
