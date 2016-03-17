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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="malkomich">
<meta name="description" content="Admin Page">

<link rel="icon" href="/favicon.ico">

<title><spring:message code="admin.page.title" /></title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">

<link href="/css/cover-logged.css" rel="stylesheet">
<link href="/css/busy-content.css" rel="stylesheet">
<link href="/css/busy-components.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="include/header-admin.jsp" />
	<div class="container">
		<div class="content">
			<div class="row">
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div id="admin-companies-button" class="admin-box"
						data-content="#admin-companies-content">
						<span class="admin-box-icon bg-red"><i
							class="ion ion-ios-people"></i></span>
						<div class="admin-box-content">
							<span class="admin-box-text"><spring:message
									code="admin.companies.box_text" /></span> <span
								class="admin-box-number">${numOfcompanies}</span> <span
								class="admin-box-loading glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>
						</div>

					</div>
					<!-- /.admin-box -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-xs-12">
					<div class="box admin-content" id="admin-companies-content">
						<div class="box-header">
							<h3 class="box-title">
								<spring:message code="admin.companies.table_title" />
							</h3>
						</div>
						<div class="box-body">
							<div class="row">
								<div class="col-sm-12">
									<table class="table" role="grid">
										<thead>
											<tr role="row">
												<th><spring:message code="company.trade_name.label" /></th>
												<th><spring:message code="company.business_name.label" /></th>
												<th><spring:message code="company.email.label" /></th>
												<th><spring:message code="company.cif.label" /></th>
												<th><spring:message code="category.name.label" /></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.content -->

		<jsp:include page="include/footer.jsp" />

	</div>
	<!-- container -->

	<!-- Message Modal -->
	<div class="modal fade bs-example-modal-lg" id="messageModal"
		tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content" id="infoMessage">
				<div class="modal-body"></div>
			</div>
		</div>
	</div>

	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		var message = '<c:out value="${messageFromController}"/>'
	</script>

	<script type="text/javascript" src="/js/busy-admin.js"></script>

</body>
</html>
