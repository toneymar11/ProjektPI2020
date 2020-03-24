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

if(isset($_POST['jelo_id']))
		{
			  if(empty($_SESSION["cart"])) {
						$_SESSION['cart']=array($_POST['jelo_id'] => 1);
					}
					else
					{
						$found = false;
						foreach ($_SESSION['cart'] as $key => $item)
						{
							if($key == $_POST['jelo_id'])
							{
								$_SESSION['cart'][$key] += 1;
								$found = true;
							}
						}
						if($found==false)
						{
							$_SESSION['cart'] += [ $_POST["jelo_id"] => 1];
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
  </head>
  <body class="goto-here">
	
  <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	    <div class="container">
	      <a class="navbar-brand" href="index.php">Burza proizvoda</a>
	      
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="oi oi-menu"></span> Izbornik
	      </button>
	      <div class="collapse navbar-collapse" id="ftco-nav">
	        <ul class="navbar-nav ml-auto">
	          <li class="nav-item"><a href="index.php" class="nav-link">Početna</a></li>
	          <li class="nav-item active"><a href="shop.php" class="nav-link">Shop</a></li>
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
            <li class="nav-item cta cta-colored"><a href="kosarica.php" class="nav-link"><span class="icon-shopping_cart"></span>
          
          [
            <?php
											if (!isset($_SESSION ['cart']))
											{
												$_SESSION ['cart'] = array ();
											}
											echo count($_SESSION ['cart']);
											?>
            
            ]
          </a></li>

	        </ul>
	      </div>
	    </div>
	</nav>
    <!-- END nav -->

    <div class="hero-wrap hero-bread" style="background-image: url('images/bg_1.jpg');">
      <div class="container">
        <div class="row no-gutters slider-text align-items-center justify-content-center">
          <div class="col-md-9 ftco-animate text-center">
          
            <h1 class="mb-0 bread">Proizvodi</h1>
          </div>
        </div>
      </div>
    </div>

    <section class="ftco-section">
    	<div class="container">
    		
    		<div class="row">
				
			<?php
                            
				$result = $mysqli->query('SELECT id, naziv, url, cijena FROM proizvodi' );
				
				if($result){
					while($obj = $result->fetch_object()){
					
						echo sprintf('

    			<div class="col-md-6 col-lg-3 ftco-animate">
    				<div class="product">
              <a href="#" class="img-prod">
              <img class="img-fluid" src="%s" alt="Colorlib Template" />
    						<div class="overlay"></div>
    					</a>
    					<div class="text py-3 pb-4 px-3 text-center">
    						<h3><a href="#">%s</a></h3>
    						<div class="d-flex">
    							<div class="pricing">
		    						<p class="price"><span>%s KM</span></p>
		    					</div>
	    					</div>
    						<div class="bottom-area d-flex px-3">
                  <div class="m-auto d-flex">
                    <form method="post" action="">
												<input type="hidden" name="jelo_id" value="%s">
                        <input type="submit" name="submit" value="Dodaj u kosaricu" class="btn-primary add-to-cart d-flex justify-content-center align-items-center text-center " />
                        
												</form>
	    					
	    						
	    							</a>
    							</div>
    						</div>
    					</div>
    				</div>
				</div> ',$obj->url, $obj->naziv, $obj->cijena, $obj->id);
					}
				}
    			?>
    		</div>
    		
    	</div>
    </section>


    
  
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


  <!-- loader -->

  <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


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