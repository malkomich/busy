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

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<meta name="description" content="New company Page">

<title><spring:message code="new-company.page.title" /></title>

<link rel="icon" href="favicon.ico">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link href="css/cover-logged.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">

</head>

<body>
	<jsp:include page="include/header.jsp" />
	
	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<spring:message code="new_company.form.title" var="formTitle" />
				<h2 class="text-center">${formTitle}</h2>

				<div class="inner cover">

					<div class="form-box">
						<form:form class="form-company" action="new_company"
							modelAttribute="companyForm" commandName="companyForm"
							method="POST">

							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="companyName.placeholder"
										var="companyNamePlaceHolder" />
									<form:input path=companyName autofocus="autofocus"
										id="companyName" tabindex="1" class="form-control"
										placeholder="${companyNamePlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="companyName" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="companyEmail.placeholder" var="companyEmailPlaceHolder" />
									<form:input path="companyEmail" id="companyEmail" tabindex="2"
										class="form-control" placeholder="${companyEmailPlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="companyEmail" cssClass="error" />
								</div>
							</div>
							
							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="companyCif.placeholder" var="companyCifPlaceHolder" />
									<form:input path="companyCif" id="companyCif" tabindex="3"
										class="form-control" placeholder="${companyCifPlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="companyCif" cssClass="error" />
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
									<form:input path="zipCode" id="zipcode" tabindex="6"
										type="tel" value="" class="form-control"
										placeholder="${zipcodePlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="zipCode" cssClass="error" />
								</div>
								<div class="col-sm-6 form-group">
									<spring:message code="phone.placeholder" var="phonePlaceHolder" />
									<form:input path="phone" id="phone" tabindex="7"
										type="tel" value="" class="form-control"
										placeholder="${phonePlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="phone" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="form-group col-sm-12">
									<spring:message code="companyAddress.placeholder"
										var="companyAddressPlaceHolder" />
									<form:input path="companyAddress" id="companyAddress"
										tabindex="8" class="form-control"
										placeholder="${companyAddressPlaceHolder}" />
									<span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
									<form:errors path="companyAddress" cssClass="error" />
								</div>
							</div>

							<div class="row">
								<div class="col-sm-12">
									<div class="col-sm-3" style="float: right">
										<spring:message code="new_company.submit" var="newCompanySubmit" />
										<button id="submit" type="submit" class="btn btn-primary"
											tabindex="9">${newCompanySubmit}</button>
									</div>
								</div>
							</div>
						</form:form>

					</div>
					<div class="footnote">
						<span class="glyphicon glyphicon-asterisk error" ></span>
						<span class="error">Campo obligatorio</span>
					</div>
					
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

	<script type="text/javascript" src="js/signup.js"></script>
</body>
</html>
