<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<meta name="description" content="Branch Page">

<link rel="icon" href="/favicon.ico">

<title><spring:message code="branch.page.title" arguments="${branch.company.tradeName}" argumentSeparator="¬" /></title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link href="/css/cover-logged.css" rel="stylesheet">
<link href="/css/busy-content.css" rel="stylesheet">
<link href="/css/busy-components.css" rel="stylesheet">
<link href="/css/calendar.min.css" rel="stylesheet">

</head>
<body>
    <jsp:include page="include/header.jsp" />
    <div class="container">
        <div class="content">

            <!-- Section selector -->
            <div class="row">
                <div class="col-sm-3 col-xs-3">
                    <button id="select-role" class="btn collapse-switch" type="button" data-toggle="collapse"
                        data-target="#role-menu" aria-expanded="false" aria-controls="#role-menu">
                        <span>${username}</span>
                    </button>
                    <div class="role-select-menu collapse" id="role-menu">
                        <div class="role-select-menu-list">
                            <a href="#" class="role-select-menu-item"> <span class="role-select-menu-item-text">${username}</span>
                            </a>
                            <c:forEach items="${roles}" var="role">
                                <a href="#" class="role-select-menu-item"> <span class="role-select-menu-item-text">${role.branch.company.tradeName}</span>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                    <!-- .role-select-menu -->
                </div>
                <!-- .col -->
            </div>
            <!-- .row -->

            <!-- Calendar Header -->
            <div class="page-header">

                <div class="pull-right form-inline">
                    <div class="btn-group">
                        <button class="btn btn-primary" data-calendar-nav="prev"><< Prev</button>
                        <button class="btn" data-calendar-nav="today">Today</button>
                        <button class="btn btn-primary" data-calendar-nav="next">Next >></button>
                    </div>
                    <div class="btn-group">
                        <button class="btn btn-warning" data-calendar-view="year">Year</button>
                        <button class="btn btn-warning active" data-calendar-view="month">Month</button>
                        <button class="btn btn-warning" data-calendar-view="week">Week</button>
                        <button class="btn btn-warning" data-calendar-view="day">Day</button>
                    </div>
                </div>

                <h3></h3>
            </div>
            <!-- .page-header -->

            <div class="row">
                <div class="span9">
                    <div id="calendar"></div>
                </div>
            </div>
            <!-- .row-profile -->

        </div>
        <!-- .content -->

        <jsp:include page="include/footer.jsp" />

    </div>
    <!-- container -->

    <!-- Calendar Events Modal -->
    <div class="modal fade" id="events-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3>Event</h3>
                </div>
                <div class="modal-body" style="height: 400px"></div>
                <div class="modal-footer">
                    <a href="#" data-dismiss="modal" class="btn">Close</a>
                </div>
            </div>
        </div>
    </div>

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
                    var message = '<c:out value="${messageFromController}"/>';
                    var branchId = "${branch.id}";
                </script>
    <script type="text/javascript" src="/js/jquery.autocomplete.min.js"></script>
    <script type="text/javascript" src="/js/busy-logic.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>
    <script type="text/javascript" src="/js/calendar.min.js"></script>
    <script type="text/javascript" src="/js/language/es-ES.js"></script>
    <script type="text/javascript" src="/js/date.js"></script>
    <script type="text/javascript" src="/js/app.js"></script>

</body>
</html>
