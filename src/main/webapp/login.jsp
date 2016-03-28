<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<link rel="icon" href="/favicon.ico">
<meta name="description" content="Login Page">

<title><spring:message code="login.page.title" /></title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link href="/css/cover.css" rel="stylesheet" type="text/css">
<link href="/css/signin.css" rel="stylesheet" type="text/css">
</head>

<body>
    <div class="site-wrapper">

        <div class="site-wrapper-inner">

            <div class="cover-container">
                <jsp:include page="include/header-index.jsp" />

                <div class="inner cover">
                    <form:form class="form-signin reduce" action="/login" method="POST" modelAttribute="loginForm"
                        commandName="loginForm">

                        <!-- Form title -->
                        <spring:message code="login.form.title" var="formTitle" />
                        <h2 class="form-signin-heading">${formTitle}</h2>

                        <div class="input-group">

                            <!-- User Icon -->
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i> </span>

                            <!-- Email Input -->
                            <spring:message code="email.placeholder" var="emailPlaceHolder" />
                            <form:input id="email" class="form-control" cssErrorClass="form-control has-error"
                                placeholder="${emailPlaceHolder}" required="required" path="email" tabindex="1" />

                            <!-- Email errors -->
                            <spring:bind path="email">
                                <c:if test="${status.error}">
                                    <c:forEach items="${status.errorMessages}" var="error">
                                        <label for="inputEmail" class="form-validation-icon error"><span
                                            class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <span
                                            id="email.errors" class="error">${error}</span></label>
                                    </c:forEach>
                                </c:if>
                            </spring:bind>

                        </div>

                        <div class="input-group">

                            <!-- Password Icon -->
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i> </span>

                            <!-- Password Input -->
                            <spring:message code="password.placeholder" var="passwordPlaceHolder" />
                            <form:password id="password" class="form-control" cssErrorClass="form-control has-error"
                                placeholder="${passwordPlaceHolder}" required="required" size="50" path="password"
                                tabindex="2" />

                            <!-- Password errors -->
                            <spring:bind path="password">
                                <c:if test="${status.error}">
                                    <c:forEach items="${status.errorMessages}" var="error">
                                        <label for="inputEmail" class="form-validation-icon error"><span
                                            class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <span
                                            id="email.errors" class="error">${error}</span></label>
                                    </c:forEach>
                                </c:if>
                            </spring:bind>

                        </div>
                        <div class="checkbox">

                            <label> <input type="checkbox" value="remember-me"> <spring:message
                                    code="remember.checkbox" />
                            </label>

                        </div>

                        <spring:message code="login.submit" var="loginSubmit" />
                        <button id="submit" class="btn btn-lg btn-primary btn-block" type="submit">${loginSubmit}</button>

                    </form:form>
                </div>

            </div>

            <jsp:include page="include/footer.jsp" />

        </div>

    </div>

    <spring:message code="signup_success.modal.title" var="modalTitle" />
    <spring:message code="signup_success.modal.content" var="modalContent" />
    <!-- SignUp Modal -->
    <div class="modal fade bs-example-modal-lg" id="signedUpModal" tabindex="-1" role="dialog"
        aria-labelledby="signedUpModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" id="signupConfirmation">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="signedUpModalLabel">${modalTitle}</h4>
                </div>
                <div class="modal-body">${modalContent}</div>
            </div>
        </div>
    </div>

    <spring:message code="auth.message.validToken" var="validationModalTitle" />
    <!-- Validation Modal -->
    <div class="modal fade bs-example-modal-lg" id="validationModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content" id="validationMessage">
                <div class="modal-body">${validationModalTitle}</div>
            </div>
        </div>
    </div>


    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript">
                    $(function() {
                        var signedUp = '${signedUp}';
                        if (signedUp) {
                            $("#signedUpModal").modal();
                        }

                        var tokenValid = '${tokenValid}';
                        if (tokenValid) {
                            $("#validationModal").modal();
                        }
                    });
                </script>
</body>
</html>
