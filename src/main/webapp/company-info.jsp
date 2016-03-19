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
<meta name="description" content="Company Page">

<link rel="icon" href="/favicon.ico">

<title><spring:message code="company_info.page.title" /></title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link href="/css/cover-logged.css" rel="stylesheet">
<link href="/css/busy-content.css" rel="stylesheet">
<link href="/css/busy-components.css" rel="stylesheet">

</head>
<body>
    <jsp:include page="include/header.jsp" />
    <div class="container">
        <div class="content">
            <div class="row profile">
                <div class="col-md-3 col-xs-12">
                    <div class="profile-sidebar col-xs-12">
                        <div class="profile-info col-md-12 col-xs-4">

                            <div class="profile-pic">
                                <img src="/img/user.png" class="img-responsive" alt="">
                            </div>

                            <div class="profile-main">
                                <div class="profile-main-title">${company.tradeName}</div>
                                <div class="profile-main-subtitle">${company.category.name}</div>
                            </div>

                            <div class="profile-actions">
                                <button type="button" class="btn btn-success btn-sm"><spring:message code="company.follow.action" /></button>
                                <button type="button" class="btn btn-danger btn-sm"><spring:message code="company.contact.action" /></button>
                            </div>

                        </div>

                        <div class="profile-menu col-md-12 col-xs-8">
                            <ul class="nav">
                                <li class="active"><a href="#"> <i class="glyphicon glyphicon-eye-open"></i> <spring:message
                                            code="company.info.nav" />
                                </a></li>
                                <li><a href="#"> <i class="glyphicon glyphicon-calendar"></i>
                                    <spring:message code="company.schedule.nav" />
                                </a></li>
                                <li><a href="#"> <i class="glyphicon glyphicon-map-marker"></i> <spring:message
                                            code="company.map.nav" />
                                </a></li>
                                <li><a href="#"> <i class="glyphicon glyphicon-user"></i> <spring:message
                                            code="company.workers.nav" />
                                </a></li>
                            </ul>
                        </div>
                        <!-- profile-menu -->

                    </div>
                    <!-- .profile-sidebar -->
                </div>
                <!-- .col -->
                <div class="col-md-9 col-xs-12">
                    <div class="profile-content">
                      <table class="table table-user-information">
                        <tbody>
                          <tr>
                            <td><spring:message code="company.info.trade_name" />:</td>
                            <td>${company.tradeName}</td>
                          </tr>
                          <tr>
                            <td><spring:message code="company.info.business_name" />:</td>
                            <td>${company.businessName}</td>
                          </tr>
                          <tr>
                            <td><spring:message code="company.info.email" />:</td>
                            <td><a href="mailto:${company.email}">${company.email}</a></td>
                          </tr>
                          <tr>
                            <td><spring:message code="company.info.cif" />:</td>
                            <td>${company.cif}</td>
                          </tr>
                            <tr>
                            <td><spring:message code="company.info.create_date" />:</td>
                            <td><fmt:formatDate type="date" dateStyle="long" value="${company.createDate.toDate()}" /></td>
                          </tr>
                          <tr>
                            <td><spring:message code="company.info.category" />:</td>
                            <td>${company.category.name}</td>
                          </tr>
                        </tbody>
                      </table>
                  </div>
                  <!-- profile-content -->
                </div>
                <!-- .col -->
            </div>
            <!-- .row-profile -->
        </div>
        <!-- .content -->

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
