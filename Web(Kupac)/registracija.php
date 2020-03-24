<?php

include 'config.php';

$ime = $_POST["ime"];
$prezime = $_POST["prezime"];
$adresa = $_POST["adresa"];
$grad = $_POST["grad"];
$broj = $_POST["broj"];
$email = $_POST["email"];
$lozinka = $_POST["lozinka"];

$sql_e = "SELECT * FROM kupci WHERE email='$email'";
$res_e = mysqli_query($mysqli, $sql_e);

if(mysqli_num_rows($res_e) > 0){
	$email_error = "Korisnički račun postoji s tom email adresom";
	echo $email_error; 
}else if($mysqli->query("INSERT INTO kupci (ime, prezime, email, lozinka, adresa, grad, broj) 
					VALUES('$ime', '$prezime', '$email', '$lozinka', '$adresa', '$grad', '$broj')"))
{
    echo'Podaci unešeni';
	echo '<br/>';
}

header ("location:index.php");

?>