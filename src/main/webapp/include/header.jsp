<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="/css/menu.css" rel="stylesheet">

<nav class="navbar navbar-inverse navbar-fixed-top">

	<div class="container">

		<div class="navbar-header">

			<!-- Toggle navigation button for small devices -->
			<button type="button" id="toggle-menu-button"
				class="navbar-toggle collapsed" data-toggle="collapse"
				data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<!-- Application icon -->
			<a class="navbar-brand" href="/"><img src="/img/busy-logo_2.png"
				height="150%"></a>

		</div>

		<!-- Navigation bar for big screens -->
		<div id="navbar" class="collapse navbar-collapse">

            <div class="navbar-form navbar-left ">
                <div class="input-group search-bar">
                    <spring:message code="navbar.search_bar.placeholder"
                        var="searchPlaceholder" />
                    <input type="text" class="form-control search-bar-text"
                        placeholder="${searchPlaceholder}">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </div>
                </div>
            </div>

			<!-- Navigation bar sections -->
			<ul class="nav navbar-nav navbar-right">

				<!-- Notifications section -->
				<li role="presentation" class="bar-li"><a
					id="notifications-switch" role="button" class="dialog-switch"
					data-target="#notifications-content"> <span
						class="glyphicon glyphicon-bell"> </span><span class="badge">${fn:length(notifications)}</span>
				</a>
					<div id="notifications-content" class="dialog">
						<div class="notifications-title">
							<h4>Notifications</h4>
						</div>
						<c:forEach items="${notifications}" var="item">
							<div class="item-notification-content">
								<h5>${item.type}</h5>
								<div class="item-notification-message">${item.message}</div>
							</div>
						</c:forEach>
					</div></li>

				<!-- User sections -->
				<li class="bar-li"><a href="#" data-toggle="dropdown"
					class="dropdown-toggle" id="userMenu" aria-haspopup="true"
					aria-expanded="true"><span><img class="img-circle"
							height="30" width="30" src="/img/user.png" alt="${user.firstName}"
							title="${user.firstName}"></span> <span id="span-username">
							${username}</span> <b class="caret"></b> </a>
					<ul class="dropdown-menu" aria-labelledby="userMenu">
						<spring:message code="navbar.profile" var="profile" />
						<li><a href="#">${profile}</a></li>
						<spring:message code="navbar.logout" var="logout" />
						<li><a id="logout-link" href="/logout">${logout}</a></li>
					</ul></li>

			</ul>
		</div>

	</div>

</nav>