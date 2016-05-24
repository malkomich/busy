<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<meta name="description" content="Main Page">

<link rel="icon" href="/favicon.ico">

<title><spring:message code="main.page.title" /></title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link href="/css/cover-logged.css" rel="stylesheet">
<link href="/css/busy-content.css" rel="stylesheet">

</head>
<body>
    <jsp:include page="include/header.jsp" />
    <div class="container">
        <div class="content">
            <div class="row">
                <c:if test="${empty roleList}">
                    <a href="new_company" id="create-company">
                        <div class="col-sm-3 col-xs-3 block-button">
                            <spring:message code="new_company.block-button" var="newCompanyBlockButton" />
                            <i class="glyphicon glyphicon-plus"></i>${newCompanyBlockButton}
                        </div>
                    </a>
                </c:if>
                <c:if test="${not empty roleList}">
                    <div class="col-sm-3 col-xs-3">
                        <button id="select-role" class="btn btn-default btn-lg collapse-switch" type="button"
                            data-toggle="collapse" data-target="#role-menu" aria-expanded="false"
                            aria-controls="#role-menu">
                            <span>${username}</span>
                        </button>
                        <div class="role-select-menu collapse" id="role-menu">
                            <div class="role-select-menu-list">
                                <a href="#" class="role-select-menu-item"> <span class="role-select-menu-item-text">${username}</span>
                                </a>
                                <c:forEach items="${roleList}" var="roleItem">
                                    <a href="/schedule/${roleItem.id}"
                                        class="role-select-menu-item"> <span class="role-select-menu-item-text">${roleItem.branch.company.tradeName}</span>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            <!-- row -->
        </div>

        <jsp:include page="include/footer.jsp" />

    </div>
    <!-- container -->

    <!-- Message Modal -->
    <div class="modal fade bs-example-modal-lg" id="messageModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content" id="infoMessage">
                <div class="modal-body"></div>
            </div>
        </div>
    </div>

    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script type="text/javascript">
                    var message = '<c:out value="${messageFromController}"/>'
                </script>
    <script type="text/javascript" src="/js/jquery.autocomplete.min.js"></script>
    <script type="text/javascript" src="/js/busy-logic.js"></script>

</body>
</html>
