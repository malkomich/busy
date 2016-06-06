<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<meta name="description" content="New company Page">

<title><spring:message code="new-company.page.title" /></title>

<link rel="icon" href="/favicon.ico">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link href="/css/cover-logged.css" rel="stylesheet">
<link href="/css/main.css" rel="stylesheet">

<link href="/css/forms.css" rel="stylesheet" type="text/css">

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
                        <form:form action="new_company" modelAttribute="companyForm"
                            commandName="companyForm" method="POST">

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="companyTradeName.placeholder" var="tradeNamePlaceHolder" />
                                    <form:input path="tradeName" autofocus="autofocus" id="tradeName" tabindex="1"
                                        class="form-control" placeholder="${tradeNamePlaceHolder}" />
                                    <form:errors path="tradeName" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="companyBusinessName.placeholder" var="businessNamePlaceHolder" />
                                    <form:input path="businessName" autofocus="autofocus" id="businessName" tabindex="2"
                                        class="form-control mandatory" placeholder="${businessNamePlaceHolder}" />
                                    <span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
                                    <form:errors path="businessName" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="companyEmail.placeholder" var="emailPlaceHolder" />
                                    <form:input path="email" id="email" tabindex="3" class="form-control mandatory"
                                        placeholder="${emailPlaceHolder}" />
                                    <span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
                                    <form:errors path="email" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="companyCif.placeholder" var="cifPlaceHolder" />
                                    <form:input path="cif" id="cif" tabindex="4" class="form-control mandatory"
                                        placeholder="${cifPlaceHolder}" />
                                    <span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
                                    <form:errors path="cif" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6 form-group">
                                    <spring:message code="country.label" var="countryLabel" />
                                    <label for="country">${countryLabel}:</label>

                                    <form:select path="countryCode" id="country" tabindex="5"
                                        class="form-control mandatory">
                                        <spring:message code="country.select.header" var="countrySelect" />
                                        <form:option value="" label="${countrySelect}" />
                                        <form:options items="${countryItems}" />
                                    </form:select>
                                    <form:errors path="countryCode" cssClass="error" />
                                </div>
                                <div class="col-sm-6 form-group">
                                    <spring:message code="city.label" var="cityLabel" />
                                    <label for="city">${cityLabel}:</label>

                                    <form:select path="cityId" id="city" tabindex="6" class="form-control mandatory">

                                        <spring:message code="city.options.empty" var="cityOptionsEmptyTag" />
                                        <form:option id="cityEmptyHeader" style="display: none;" value=""
                                            label="${cityOptionsEmptyTag}" />

                                        <spring:message code="city.select.header" var="citySelect" />
                                        <form:option id="cityNotEmptyHeader" style="display: none;" value=""
                                            label="${citySelect}" />

                                    </form:select>
                                    <form:errors path="cityId" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6 form-group">
                                    <spring:message code="zipcode.placeholder" var="zipcodePlaceHolder" />
                                    <form:input path="zipCode" id="zipcode" tabindex="7" type="tel" value=""
                                        class="form-control" placeholder="${zipcodePlaceHolder}" />
                                    <form:errors path="zipCode" cssClass="error" />
                                </div>
                                <div class="col-sm-6 form-group">
                                    <spring:message code="phone.placeholder" var="phonePlaceHolder" />
                                    <form:input path="phone" id="phone" tabindex="8" type="tel" value=""
                                        class="form-control" placeholder="${phonePlaceHolder}" />
                                    <form:errors path="phone" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="companyAddress1.placeholder" var="address1PlaceHolder" />
                                    <form:input path="address1" id="address1" tabindex="9" class="form-control"
                                        placeholder="${address1PlaceHolder}" />
                                    <form:errors path="address1" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="companyAddress2.placeholder" var="address2PlaceHolder" />
                                    <form:input path="address2" id="address2" tabindex="10" class="form-control"
                                        placeholder="${address2PlaceHolder}" />
                                    <form:errors path="address2" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <spring:message code="category.label" var="categoryLabel" />
                                    <label for="category">${categoryLabel}:</label>

                                    <form:select path="categoryId" id="category" tabindex="11" class="form-control">
                                        <spring:message code="category.select.header" var="categorySelect" />
                                        <form:option value="" label="${categorySelect}" />
                                        <form:options items="${categoryItems}" />
                                    </form:select>
                                    <form:errors path="categoryId" cssClass="error" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="col-sm-3" style="float: right">
                                        <spring:message code="new_company.submit" var="newCompanySubmit" />
                                        <button id="submit" type="submit" class="btn btn-primary" tabindex="12">${newCompanySubmit}</button>
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

                <jsp:include page="include/footer.jsp" />

            </div>

        </div>
    </div>

    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="/js/jquery.autocomplete.min.js"></script>
    <script type="text/javascript" src="/js/forms.js"></script>
    <script type="text/javascript" src="/js/busy-logic.js"></script>

</body>
</html>
