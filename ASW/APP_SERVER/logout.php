<?php
session_start();
if(isset($_SESSION['auth'])){
  unset($_SESSION['auth']);
  unset($_SESSION['user']);
  }
  session_unset();
session_destroy();
header("Location: log.php");
?>
