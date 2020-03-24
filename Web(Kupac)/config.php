<?php
$currency = 'KM';
$db_username = 'bruno';
$db_password = '1234';
$db_name = 'pis';
$db_host = 'sql5';
$mysqli = mysqli_connect($db_host, $db_username, $db_password,$db_name);
mysqli_set_charset($mysqli,"utf8");
?>
