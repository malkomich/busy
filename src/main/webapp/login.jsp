<!--
********** BOOTSTRAP LICENSE **********

The MIT License (MIT)

Copyright (c) 2011-2015 Twitter, Inc

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
***************************************** -->

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<link rel="icon" href="favicon.ico">
<meta name="description" content="Login Page">

<title><spring:message code="login.page.title" /></title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link href="css/cover.css" rel="stylesheet" type="text/css">
<link href="css/signin.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">
				<jsp:include page="include/header-index.jsp" />

				<div class="inner cover">
					<form:form class="form-signin reduce" action="/" method="POST"
						modelAttribute="loginForm" commandName="loginForm">

						<!-- Form title -->
						<spring:message code="login.form.title" var="formTitle" />
						<h2 class="form-signin-heading">${formTitle}</h2>

						<div class="input-group">

							<!-- User Icon -->
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i> </span>

							<!-- Email Input -->
							<spring:message code="email.placeholder" var="emailPlaceHolder" />
							<form:input id="email" type="email" class="form-control"
								placeholder="${emailPlaceHolder}" required="required"
								path="email" />

							<!-- Email errors -->
							<spring:bind path="email">
								<c:if test="${status.error}">
									<c:forEach items="${status.errorMessages}" var="error">
										<label for="inputEmail" class="form-validation-icon error"><span
											class="glyphicon glyphicon-exclamation-sign"
											aria-hidden="true"></span> <span id="email.errors"
											class="error">${error}</span></label>
									</c:forEach>
								</c:if>
							</spring:bind>

						</div>

						<div class="input-group">

							<!-- Password Icon -->
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i> </span>

							<!-- Password Input -->
							<spring:message code="password.placeholder"
								var="passwordPlaceHolder" />
							<form:password id="password" class="form-control"
								placeholder="${passwordPlaceHolder}" required="required" size="50"
								path="password" />

							<!-- Password errors -->
							<spring:bind path="password">
								<c:if test="${status.error}">
									<c:forEach items="${status.errorMessages}" var="error">
										<label for="inputEmail" class="form-validation-icon error"><span
											class="glyphicon glyphicon-exclamation-sign"
											aria-hidden="true"></span> <span id="email.errors"
											class="error">${error}</span></label>
									</c:forEach>
								</c:if>
							</spring:bind>

						</div>
						<div class="checkbox">

							<label> <input type="checkbox" value="remember-me">
								<spring:message code="remember.checkbox" />
							</label>

						</div>

						<spring:message code="login.submit" var="loginSubmit" />
						<button id="submit" class="btn btn-lg btn-primary btn-block" type="submit">
							${loginSubmit}</button>

					</form:form>
				</div>

			</div>

			<jsp:include page="include/footer.jsp" />

		</div>

	</div>



	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
