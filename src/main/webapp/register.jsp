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

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html class="logged-out  signup">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<link rel="icon" href="favicon.ico">
<meta name="description" content="Register Page">

<title><spring:message code="register.page.title" /></title>

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

				<spring:message code="register.form.title" var="formTitle" />
				<h2 class="text-center">${formTitle}</h2>

				<div class="inner cover">

					<div class="form-box">
						<form:form class="form-signin" action="register"
							commandName="userRegisterForm" method="POST">
							<form:errors path="" element="p" />
							<dl>
								<dd>
									<spring:message code="firstname.placeholder"
										var="firstnamePlaceHolder" />
									<form:input path="firstname" autofocus="autofocus"
										id="firstname" size="20" tabindex="1" class="form-control"
										placeholder="${firstnamePlaceHolder}" />
								</dd>

								<dd>
									<spring:message code="lastname.placeholder"
										var="lastnamePlaceHolder" />
									<form:input path="lastname" id="lastname" size="30"
										tabindex="2" class="form-control"
										placeholder="${lastnamePlaceHolder}" />
								</dd>

								<dd>
									<spring:message code="email.placeholder" var="emailPlaceHolder" />
									<form:input path="email" id="email" size="30" tabindex="3"
										class="form-control" placeholder="${emailPlaceHolder}" />
								</dd>

								<dd>
									<spring:message code="nif.placeholder" var="nifPlaceHolder" />
									<form:input path="nif" id="nif" size="30" tabindex="4"
										class="form-control" placeholder="${nifPlaceHolder}" />
								</dd>

								<dt>
									<spring:message code="country.placeholder"
										var="countryPlaceHolder" />
									<label for="country">${countryPlaceHolder}</label><em>*</em>
								</dt>
								<dd>
									<form:select path="country" id="country" tabindex="5"
										class="form-control" items="" />
								</dd>

								<dt>
									<spring:message code="city.placeholder" var="cityPlaceHolder" />
									<label for="city">${cityPlaceHolder}</label><em>*</em>
								</dt>
								<dd>
									<form:select path="city" id="city" tabindex="6"
										class="form-control" items="" />
								</dd>

								<dd>
									<spring:message code="zipcode.placeholder"
										var="zipcodePlaceHolder" />
									<form:input path="zipcode" id="zipcode" size="7" tabindex="5"
										type="tel" value="" class="form-control"
										placeholder="${zipcodePlaceHolder}" />
								</dd>

								<dd>
									<spring:message code="phone.placeholder" var="phonePlaceHolder" />
									<form:input path="phone" id="user_phone" size="8" tabindex="5"
										type="tel" value="" class="form-control"
										placeholder="${phonePlaceHolder}" />
								</dd>

								<dd>
									<spring:message code="password.placeholder"
										var="passwordPlaceHolder" />
									<form:input path="password" id="user_password" size="30"
										tabindex="9" type="password" class="form-control"
										placeholder="${passwordPlaceHolder}" />
								</dd>

								<dd>
									<spring:message code="passwordConfirm.placeholder"
										var="passConfirmPlaceHolder" />
									<form:input path="confirmedPassword"
										id="user_confirmedPassword" size="30" tabindex="10"
										type="password" class="form-control"
										placeholder="${passConfirmPlaceHolder}" />
								</dd>
							</dl>

							<div class="image-block tos-privacy-submit">
								<div class="bd privacy-link-wrapper">
									<spring:message code="signup.info" var="signupInfo" />
									<p>${signupInfo}</p>
								</div>
								<div class="img-ext">
									<spring:message code="register.button" var="signupButton" />
									<button id="submit" type="submit" class="btn btn-primary" tabindex="7"
										style="float: right;">${signupButton}</button>
								</div>
							</div>
						</form:form>

					</div>
				</div>
				<div class="login-tip">
					¿Ya tienes una cuenta? <a href="/">Iniciar sesión aquí</a>
				</div>

				<jsp:include page="../include/footer.jsp" />
			</div>

		</div>
	</div>

	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
