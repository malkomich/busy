<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="clearfix">
	<div class="inner">
		<a href="/"><img src="img/busy-logo_2.png" align="left"></a>
		<nav>
			<ul class="nav masthead-nav">
				<li>
					<form action="/signup" method="get">

						<spring:message code="signup.button" var="signupButton" />
						<button id="signupButton" type="submit" class="btn btn-lg btn-primary btn-block">
							${signupButton}</button>

					</form>
				</li>
			</ul>
		</nav>
	</div>
</div>