<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<form:form class="form-signin service-form" id="serviceForm"
	action="javascript:saveServices()" modelAttribute="serviceForm"
	commandName="serviceForm" method="POST">

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">
			<form:label path="date">
				<joda:format value="${serviceForm.date}" pattern="dd/MM/yyyy" />
			</form:label>
		</h4>
	</div>

	<div class="modal-body">

		<c:forEach items="${serviceForm.services}" var="service"
			varStatus="status">
			<div class="row service-row">
				<div class="col-sm-1 form-group">
					<span class="glyphicon glyphicon-time"></span>
				</div>

				<div class="col-sm-2 form-group">
					<div class="row">
						<spring:message code="schedule.service.start_time.placeholder"
							var="startTimePlaceholder" />
						<form:input path="services[${status.index}].startTime"
							id="startTime" type="time" tabindex="1" class="form-control"
							placeholder="${startTimePlaceholder}"
							value="${service.startTime}" />
					</div>
					<div class="row service-endTime"></div>
				</div>

				<div class="col-sm-2 form-group">
					<form:select path="services[${status.index}].serviceType"
						id="serviceType" tabindex="2" class="form-control">
						<spring:message code="schedule.service.service_type.select"
							var="serviceTypeSelect" />
						<form:option value="" label="${serviceTypeSelect}" />
						<c:choose>
							<c:when test="${empty services[status.index].serviceType}">
								<form:options items="${serviceTypes}" />
							</c:when>
							<c:otherwise>
								<form:options items="${serviceTypes}"
									itemValue="${service.serviceType}" />
							</c:otherwise>
						</c:choose>
					</form:select>
					<form:errors path="services[${status.index}].serviceType"
						cssClass="error" />
				</div>

				<div class="col-sm-2 form-group">
					<div class="row">
						<a id="role-dropdown" href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown"
							aria-expanded="false"><spring:message
								code="schedule.service.roles.dropdown" /> <b class="caret"></b></a>
						<ul class="dropdown-menu role-list" role="menu"
							aria-labelledby="role-dropdown">
							<c:forEach items="${serviceForm.existingRoles}" var="branchRole">
								<c:set var="checked" value="false" />
								<c:forEach var="item" items="${service.roles}">
									<c:if test="${item eq branchRole}">
										<c:set var="contains" value="true" />
									</c:if>
								</c:forEach>
								<li class="role-item"><input type="checkbox"
									value="${branchRole.id}" checked="${checked}">${branchRole}</li>
							</c:forEach>
						</ul>
						<form:errors path="services[${status.index}].roles"
							cssClass="error" />
					</div>
					<div class="row service-roles-summary">
						<ul>
						<c:forEach var="item" items="${service.roles}">
							<li>${item}</li>
						</c:forEach>
						</ul>
					</div>
				</div>

				<div class="col-sm-2 form-group">
					<c:choose>
						<c:when test="${service.repeated}">
							<button type="button" class="btn btn-default">
								<spring:message code="modal.close" />
							</button>
						</c:when>
						<c:otherwise>
							<form:select path="services[${status.index}].repetitionType"
								id="repetitionType" tabindex="4" class="form-control">
								<c:forEach items="${serviceForm.existingRepetitionTypes}"
									var="rType">
									<form:option value="${rType.msgCode}">
										<spring:message code="${rType.msgCode}" />
									</form:option>
								</c:forEach>
							</form:select>
						</c:otherwise>
					</c:choose>

				</div>

				<div class="col-sm-2 form-group">
					<form:input path="services[${status.index}].repetitionDate"
						id="repetitionDate" type="date" tabindex="5" class="form-control"
						value="${service.repetitionDate}" />
					<form:errors path="services[${status.index}].repetitionDate"
						cssClass="error" />
				</div>
			</div>
			<!-- .service-row -->
		</c:forEach>

		<div class="row service-row">
			<div class="col-sm-1 form-group">
				<span class="glyphicon glyphicon-time"></span>
			</div>

			<div class="col-sm-1 form-group">
				<a href="javascript:newService()"><span
					class="service-add glyphicon glyphicon-plus"></span></a>
			</div>

		</div>

	</div>
	<!-- .modal-body -->

	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			<spring:message code="modal.close" />
		</button>
		<button id="submit" type="submit" class="btn btn-primary" tabindex="6">
			<spring:message code="form.save" />
		</button>
	</div>
</form:form>

<script type="text/javascript" src="/js/signup.js"></script>