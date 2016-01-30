<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<nav class="navbar navbar-inverse navbar-fixed-top">

	<div class="container">

		<div class="navbar-header">

			<!-- Toggle navigation button for small devices -->
			<button type="button" id="toggle-menu-button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<!-- Application icon -->
			<a class="navbar-brand" href="/"><img src="img/busy-logo_2.png"
				height="150%"></a>

		</div>

		<!-- Navigation bar for big screens -->
		<div id="navbar" class="collapse navbar-collapse">

			<!-- Navigation bar sections -->
			<ul class="nav navbar-nav navbar-right">

				<!-- User sections -->
				<li class="bar-li"><a href="#" data-toggle="dropdown"
					class="dropdown-toggle" id="userMenu" aria-haspopup="true"
					aria-expanded="true"><span><img class="img-circle"
							height="30" width="30"
							src="img/user.png"
							alt="${user.firstName}" title="${user.firstName}"></span> <span id="span-username">
							${user.firstName}</span> <b class="caret"></b> </a>
					<ul class="dropdown-menu" aria-labelledby="userMenu">
						<spring:message code="navbar.profile" var="profile" />
						<li><a href="#">${profile}</a></li>
						<spring:message code="navbar.logout" var="logout" />
						<li><a id="logout-link" href="/logout">${logout}</a></li>
					</ul></li>

				<!-- Notifications section -->
				<li role="presentation" class="bar-li"><a href="#"><span
						class="glyphicon glyphicon-bell"> </span><span class="badge">3</span></a></li>

			</ul>
		</div>

	</div>
</nav>