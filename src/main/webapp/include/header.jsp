<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/"><img src="img/busy-logo_2.png"
				height="150%"></a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="bar-li"><a href="#" data-toggle="dropdown"
					class="dropdown-toggle" id="userMenu" aria-haspopup="true"
					aria-expanded="true"><span><img class="img-circle"
							height="30" width="30"
							src="https://trello-avatars.s3.amazonaws.com/26b1569836c6a987d4b9d77ac0ca1c79/30.png"
							alt="${user.name}" title="${user.name}"></span> <span>
							${user.name}</span> <b class="caret"></b> </a>
					<ul class="dropdown-menu" aria-labelledby="userMenu">
						<li><a href="#">Perfil</a></li>
						<li><a href="/logout">Cerrar Sesión</a></li>
					</ul></li>
				<li role="presentation" class="bar-li"><a href="#"><span
						class="glyphicon glyphicon-bell"> </span><span class="badge">3</span></a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</nav>