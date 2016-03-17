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
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<link rel="icon" href="/favicon.ico">
<meta name="description" content="Signup Page">

<title><spring:message code="signup.page.title" /></title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link href="/css/cover.css" rel="stylesheet" type="text/css">
<link href="/css/signin.css" rel="stylesheet" type="text/css">
</head>

<body>

	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<spring:message code="signup.form.title" var="formTitle" />
				<h2 class="text-center">${formTitle}</h2>

				<div class="inner cover">

					<div class="form-box">
						<form:form class="form-signin" action="signup"
							modelAttribute="signupForm" commandName="signupForm"
							method="POST">

							<div class="row">
								<div class="col-sm-6 form-group">
									<spring:message code="firstname.placeholder"
										var="firstnamePlaceHolder" />
									<form:input path="firstName" autofocus="autofocus"
										id="firstname" tabindex="1" class="form-control mandatory"
										placeholder="${firstnamePlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="firstName" cssClass="error" />
								</div>
								<div class="col-sm-6 form-group">
									<spring:message code="lastname.placeholder"
										var="lastnamePlaceHolder" />
									<form:input path="lastName" id="lastname" tabindex="2"
										class="form-control mandatory"
										placeholder="${lastnamePlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="lastName" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="email.placeholder" var="emailPlaceHolder" />
									<form:input path="email" id="email" tabindex="3"
										class="form-control mandatory"
										placeholder="${emailPlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="email" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="col-sm-6 form-group">
									<spring:message code="country.label" var="countryLabel" />
									<label for="country">${countryLabel}:</label>

									<form:select path="countryCode" id="country" tabindex="4"
										class="form-control">
										<spring:message code="country.select.header"
											var="countrySelect" />
										<form:option value="" label="${countrySelect}" />
										<form:options items="${countryItems}" />
									</form:select>
									<form:errors path="countryCode" cssClass="error" />
								</div>
								<div class="col-sm-6 form-group">
									<spring:message code="city.label" var="cityLabel" />
									<label for="city">${cityLabel}:</label>

									<form:select path="cityId" id="city" tabindex="5"
										class="form-control">

										<spring:message code="city.options.empty"
											var="cityOptionsEmptyTag" />
										<form:option id="cityEmptyHeader" style="display: none;"
											value="" label="${cityOptionsEmptyTag}" />

										<spring:message code="city.select.header" var="citySelect" />
										<form:option id="cityNotEmptyHeader" style="display: none;"
											value="" label="${citySelect}" />

									</form:select>
									<form:errors path="cityId" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="col-sm-6 form-group">
									<spring:message code="zipcode.placeholder"
										var="zipcodePlaceHolder" />
									<form:input path="zipCode" id="zipcode" tabindex="6" type="tel"
										value="" class="form-control"
										placeholder="${zipcodePlaceHolder}" />
									<form:errors path="zipCode" cssClass="error" />
								</div>
								<div class="col-sm-6 form-group">
									<spring:message code="phone.placeholder" var="phonePlaceHolder" />
									<form:input path="phone" id="phone" tabindex="7" type="tel"
										value="" class="form-control"
										placeholder="${phonePlaceHolder}" />
									<form:errors path="phone" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="password.placeholder"
										var="passwordPlaceHolder" />
									<form:input path="password" id="password" tabindex="8"
										type="password" class="form-control mandatory"
										placeholder="${passwordPlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="password" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="passwordConfirm.placeholder"
										var="passConfirmPlaceHolder" />
									<form:input path="confirmedPassword" id="confirmedPassword"
										tabindex="9" type="password" class="form-control mandatory"
										placeholder="${passConfirmPlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="confirmedPassword" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="col-sm-12">
									<div class="col-sm-8">
										<spring:message code="signup.info" var="signupInfo" />
										<p>${signupInfo}</p>
									</div>
									<div class="col-sm-3" style="float: right">
										<spring:message code="signup.submit" var="signupSubmit" />
										<button id="submit" type="submit" class="btn btn-primary"
											tabindex="10">${signupSubmit}</button>
									</div>
								</div>
							</div>
						</form:form>

					</div>
					<div class="footnote">
						<span class="glyphicon glyphicon-asterisk error"></span>
						<spring:message code="mandatory.info" var="mandatoryInfo" />
						<span class="error">${mandatoryInfo}</span>
					</div>

				</div>
				<spring:message code="signup.logintip.label" var="loginLabel" />
				<spring:message code="signup.logintip.link" var="loginLink" />
				<div class="login-tip" style="border-top: 1px solid #888;">
					${loginLabel}<a href="/login"><b>${loginLink}</b></a>
				</div>

				<jsp:include page="include/footer.jsp" />
			</div>

		</div>
	</div>

	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="/js/signup.js"></script>
</body>
</html>
