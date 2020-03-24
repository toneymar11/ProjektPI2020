
<?php 
include 'config.php';
session_start();
 if(isset($_POST['submit_prijava']))
	{
		$email = $_POST["email"];
		$lozinka = $_POST["lozinka"];
		$flag = 'true';

		$result = $mysqli->query('SELECT id,email,lozinka,ime FROM kupci');

		if($result){
			while($obj = $result->fetch_object()){
				if($obj->email == $email && $obj->lozinka == $lozinka) {
					$_SESSION['email'] = $email;
					$_SESSION['id'] = $obj->id;
					$_SESSION['ime'] = $obj->ime;
					
					
				} 
			}
		}
    
	}
?>



<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Burza proizvoda</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
	<link rel="stylesheet" href="css/animate.css">
	
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/owl.theme.default.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">

    <link rel="stylesheet" href="css/aos.css">

    <link rel="stylesheet" href="css/ionicons.min.css">

    <link rel="stylesheet" href="css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="css/jquery.timepicker.css">

    
    <link rel="stylesheet" href="css/flaticon.css">
    <link rel="stylesheet" href="css/icomoon.css">
	<link rel="stylesheet" href="css/style.css">
	
<style>
	.navigation{
	width: 100%;
	background-color: black;
	margin-right: 10%;
	}
	.logout{
	font-size: .10em;
	font-family: 'Oswald', sans-serif;
		position: relative;
	right: -18px;
	bottom: -4px;
	overflow: hidden;
	letter-spacing: 4px;
	opacity: 0;
	transition: opacity .45s;
	-webkit-transition: opacity .35s;
	
	}

	.button{
		text-decoration: none;
		float: right;
	padding: 12px;
	margin: 15px;
	color: white;
	width: 25px;
	background-color: black;
	transition: width .35s;
	-webkit-transition: width .35s;
	overflow: hidden
	} 

	a:hover .logout {
	width: 100px;
	}

	a:hover .logout{
	opacity: .9;
	}

	a{
	text-decoration: none;
	}
 
	.emp-profile{
		padding: 3%;
		margin-top: 3%;
		margin-bottom: 3%;
		border-radius: 0.5rem;
		background: #fff;
	}
	.profile-img{
		text-align: center;
	}
	.profile-img img{
		width: 70%;
		height: 100%;
	}
	.profile-img .file {
		position: relative;
		overflow: hidden;
		margin-top: -20%;
		width: 70%;
		border: none;
		border-radius: 0;
		font-size: 15px;
		background: #212529b8;
	}
	.profile-img .file input {
		position: absolute;
		opacity: 0;
		right: 0;
		top: 0;
	}
	.profile-head h5{
		color: #333;
	}
	.profile-head h6{
		color: #0062cc;
	}
	.profile-edit-btn{
		border: none;
		border-radius: 1.5rem;
		width: 70%;
		padding: 2%;
		font-weight: 600;
		color: #6c757d;
		cursor: pointer;
	}
	.proile-rating{
		font-size: 12px;
		color: #818182;
		margin-top: 5%;
	}
	.proile-rating span{
		color: #495057;
		font-size: 15px;
		font-weight: 600;
	}
	.profile-head .nav-tabs{
		margin-bottom:5%;
	}
	.profile-head .nav-tabs .nav-link{
		font-weight:600;
		border: none;
	}
	.profile-head .nav-tabs .nav-link.active{
		border: none;
		border-bottom:2px solid #0062cc;
	}
	.profile-work{
		padding: 14%;
		margin-top: -15%;
	}
	.profile-work p{
		font-size: 12px;
		color: #818182;
		font-weight: 600;
		margin-top: 10%;
	}
	.profile-work a{
		text-decoration: none;
		color: #495057;
		font-weight: 600;
		font-size: 14px;
	}
	.profile-work ul{
		list-style: none;
	}
	.profile-tab label{
		font-weight: 600;
	}
	.profile-tab p{
		font-weight: 600;
		color: #0062cc;
	}
</style>



</head>
  
	<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	    <div class="container">
	      <a class="navbar-brand" href="index.php">Burza proizvoda</a>
	      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="oi oi-menu"></span> Izbornik
	      </button>

	      <div class="collapse navbar-collapse" id="ftco-nav">
	        <ul class="navbar-nav ml-auto">
	          <li class="nav-item active"><a href="index.php" class="nav-link">Početna</a></li>
	          <li class="nav-item"><a href="shop.php" class="nav-link">Shop</a></li>
	          <li class="nav-item"><a href="about.php" class="nav-link">O nama</a></li>
			  <li class="nav-item"><a href="contact.php" class="nav-link">Kontakt</a></li>
			  
			  <div class="collapse navbar-collapse" id="ftco-nav">
				  <?php
				  							if(isset($_GET["logout"]))
											  {
												  session_start();
												  session_unset();
												 session_destroy();
												  header("location:index.php");
												  exit();
											  }
			  								if(!isset($_SESSION['email']))
			   	
											{
												echo ' <a class="dropdown-item" href="#" data-toggle="modal" data-target="#exampleModalCenter">';
												echo ' <span class="prijava" id="user" value="null">Prijava&nbsp&nbsp</span>';
											}
											else
											{
												echo '<a class="dropdown-item" href="profile.php">';
												echo '<span class="prijava" id="user" value="' . $_SESSION['email'] . '">'. $_SESSION['ime'] .'&nbsp&nbsp</span>';
											}											
											?>
			
				<i class="fa fa-user"></i> </a>
			</div>
			  <li class="nav-item cta cta-colored"><a href="kosarica.php" class="nav-link"><span class="icon-shopping_cart"></span>[ 
			  <?php
						if (!isset($_SESSION ['cart']))
						{
							$_SESSION ['cart'] = array ();
						}
						echo count($_SESSION ['cart']);
			?>]</a></li>

	        </ul>
	      </div>
	    </div>
    </nav>
      <!-- END nav -->
	

 <div class="container emp-profile">
            <form method="post">
                <div class="row">
                    
                    <div class="col-md-6">
                        <div class="profile-head">
                                    
                                
                                  
                            <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Osobni podaci</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Narudžbe</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-2">
					     <a class="btn btn-danger" href="index.php?logout=1">
						   Odjava
										</a>
                    </div>
                </div>
                <div class="row">
                   
                    <div class="col-md-8">
                        <div class="tab-content profile-tab" id="myTabContent">
                            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
							<div class="row">
		
		<div class="col-md-9">
		    <div class="card">
		        <div class="card-body">
		            <div class="row">
		                <div class="col-md-12">
		                    <h4>Vaš profil</h4>
		                    <hr>
		                </div>
		            </div>
		            <div class="row">
		                <div class="col-md-12">
							<?php
							
							$updated = false;
							if(isset($_POST['spremi'])){                                    
								$ime = $_POST["ime"];
								$prezime = $_POST["prezime"];
								$adresa = $_POST["adresa"];
								$grad = $_POST["grad"];
								$broj = $_POST["broj"];
								$email = $_POST["email"];
								$lozinka = $_POST["lozinka"];
								$upid= $_SESSION['id'];
												
								$query="UPDATE kupci SET ime='".$ime."', prezime='".$prezime."',email='".$email."', adresa='".$adresa."',
										grad='".$grad."',lozinka='".$lozinka."',broj=$broj WHERE id=$upid";
												
								$result = mysqli_query($mysqli, $query);
												
												
								if($result)
								{
									$updated = true;
									$_SESSION['ime']=$ime;
								}
								else
								{
									$updated = false;
								}                                    
                                        
							}

							$user_id = $_SESSION['id'];
							$query = 'SELECT * FROM kupci where id='.$user_id;
								$result = $mysqli->query($query);

								if($result){
									while($obj = $result->fetch_object()){

										echo '<form action="profile.php" method="post">
										<div class="form-group">
											<label>Ime</label>
			
											<input type="text" class="form-control" name="ime" value="'.$obj->ime.'" required="">
										</div>
										<div class="form-group">
											<label>Prezime</label>
											<input type="text" class="form-control" name="prezime" value="'.$obj->prezime.'" required="">
										</div>
										<div class="form-group">
											<label>Email</label>
											<input type="text" class="form-control" name="email" value="'.$obj->email.'" required="">
										</div>
			
										<div class="form-group">
											<label class="mb-2">Lozinka</label>
											<input type="password" class="form-control" name="lozinka"  value="'.$obj->lozinka.'" required="">
										</div>
										<div class="form-group">
											<label>Adresa</label>
											<input type="text" class="form-control" name="adresa" value="'.$obj->adresa.'" required="">
										</div>
										<div class="form-group">
											<label>Grad</label>
											<input type="text" class="form-control" name="grad" value="'.$obj->grad.'" required="">
										</div>
										<div class="form-group">
											<label>Broj</label>
											<input type="text" class="form-control" name="broj" value="'.$obj->broj.'" required="">
										</div>
									  <div class="form-group row">
										<div class="offset-4 col-8">
										  <input type="submit"  name="spremi" class="btn btn-primary" value="Ažuriraj profil" />
										</div>
									  </div>
									</form>';
									}
								}
							?>
						
		                </div>
		            </div>
		            
		        </div>
		    </div>
		</div>
	</div>
                                        
                            </div>
                            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
								<?php
								 	$query = 'SELECT * FROM narudzbe where id_kupca='.$user_id;
									$result = $mysqli->query($query);
	 
									if($result){
										while($obj = $result->fetch_object()){

											$queryy = 'SELECT * FROM proizvodi where id="'.$obj->id_proizvoda.'"';
											$resultt = $mysqli->query($queryy);
											if($resultt){
												while($objj = $resultt->fetch_object()){
													$tot = intval($obj->kolicina) * intval($objj->cijena);
													echo'<p>'.$objj->naziv.' |'.$obj->kolicina.' | '.$obj->odobreno.' '.$tot.'</p>';

												}
											}

										}
									}
									if(mysqli_num_rows($result) == 0){
										echo'<p>Ovdje bi trebale narudžbe</p> ';
									}
										
									
								?> 
								    
                                
                            </div>
                        </div>
                    </div>
                </div>
            </form>           
        </div>

	
	







	<?php
		if(isset($_POST['logout'])){
			session_start();
            session_unset();
            session_destroy();
            header("location:index.php");
            exit();
		}

	?>

    

		

		<!--/Login-->
		<div class="modal fade" id="exampleModalCenter" tabindex="" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header text-center">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
	
						<div class="login px-4 mx-auto mw-100">
							<h5 class="text-center mb-4">Prijavi se</h5>
							<form method="POST" action="">
								<div class="form-group">
									<label class="mb-2">Email </label>
									<input type="email" class="form-control" name="email" placeholder="" required="true">
									<small id="emailHelp" class="form-text text-muted"></small>
								</div>
								<div class="form-group">
									<label class="mb-2">Lozinka</label>
									<input type="password" class="form-control" name="lozinka" placeholder="" required="true">
								</div>
								<div class="form-check mb-2">
									<input type="checkbox" class="form-check-input">
									<label class="form-check-label" for="exampleCheck1">Zapamti me</label>
								</div>
								<button type="submit" class="btn btn-primary submit mb-4" name="submit_prijava">Prijavi se</button>
								<p class="text-center pb-4">
									<a href="#" data-toggle="modal" data-target="#exampleModalCenter2"> Nemate račun? Pređite na registraciju.</a>
								</p>
							</form>
						</div>
					</div>
	
				</div>
			</div>
		</div>
		<!--//Login-->
		<!--/Register-->
		<div class="modal fade" id="exampleModalCenter2" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header text-center">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="login px-4 mx-auto mw-100">
							<h5 class="text-center mb-4">Registriraj se</h5>
							<form action="registracija.php" method="post">
								<div class="form-group">
									<label>Ime</label>
	
									<input type="text" class="form-control" name="ime" placeholder="" required="">
								</div>
								<div class="form-group">
									<label>Prezime</label>
									<input type="text" class="form-control" name="prezime" placeholder="" required="">
								</div>
								<div class="form-group">
									<label>Email</label>
									<input type="text" class="form-control" name="email" placeholder="" required="">
								</div>
	
								<div class="form-group">
									<label class="mb-2">Lozinka</label>
									<input type="password" class="form-control" name="lozinka"  placeholder="" required="">
								</div>
								<div class="form-group">
									<label>Adresa</label>
									<input type="text" class="form-control" name="adresa" placeholder="" required="">
								</div>
								<div class="form-group">
									<label>Grad</label>
									<input type="text" class="form-control" name="grad" placeholder="" required="">
								</div>
								<div class="form-group">
									<label>Broj</label>
									<input type="text" class="form-control" name="broj" placeholder="" required="">
								</div>
								
								<button type="submit" class="btn btn-primary submit mb-4">Registriraj se</button>
								<p class="text-center pb-4">
									<a href="#">Prihvaćam uvjete</a>
								</p>
							</form>
	
						</div>
					</div>
	
				</div>
			</div>
		</div>
		<!--//Register-->
   

    <hr>

  <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>
 
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="js/jquery.min.js"></script>
  <script src="js/jquery-migrate-3.0.1.min.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/jquery.easing.1.3.js"></script>
  <script src="js/jquery.waypoints.min.js"></script>
  <script src="js/jquery.stellar.min.js"></script>
  <script src="js/owl.carousel.min.js"></script>
  <script src="js/jquery.magnific-popup.min.js"></script>
  <script src="js/aos.js"></script>
  <script src="js/jquery.animateNumber.min.js"></script>
  <script src="js/bootstrap-datepicker.js"></script>
  <script src="js/scrollax.min.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>
	
  

  


  </body>
</html>