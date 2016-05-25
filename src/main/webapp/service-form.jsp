<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<form:form class="service-form" id="serviceForm" action="javascript:saveServices()" modelAttribute="serviceForm"
    commandName="serviceForm" method="POST">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">
            <form:label path="date">
                <joda:format value="${serviceForm.date}" pattern="dd/MM/yyyy" />
            </form:label>
        </h4>
    </div>

    <div class="modal-body">

        <c:forEach items="${serviceForm.services}" var="service" varStatus="status">
            <div class="row service-row">

                <div class="col-sm-3 form-group">
                    <div class="col-sm-4">
                        <span class="glyphicon glyphicon-time center-v"></span>
                    </div>
                    <div class="col-sm-8">
                        <div class="row">
                            <spring:message code="schedule.service.tooltip.start_time" var="startTimeTip" />
                            <form:input path="services[${status.index}].startTime" id="startTime" type="time"
                                tabindex="1" class="form-control" data-toggle="tooltip" data-placement="left"
                                title="${startTimeTip}" />
                            <form:errors path="services[${status.index}].startTime" cssClass="error fs-xs" />
                        </div>
                        <div class="row">
                            <spring:message code="schedule.service.tooltip.end_time" var="endTimeTip" />
                            <input id="endTime" type="time" tabindex="-1" class="form-control" readonly="true"
                                data-toggle="tooltip" data-placement="left" title="${endTimeTip}" />
                        </div>
                    </div>
                </div>
                <!-- .form-group (TIME) -->

                <div class="col-sm-3 form-group">
                    <spring:message code="schedule.service.tooltip.service_type" var="sTypeTip" />
                    <form:select path="services[${status.index}].serviceType" id="serviceType" tabindex="2"
                        class="form-control" data-toggle="tooltip" data-placement="top" title="${sTypeTip}">
                        <spring:message code="schedule.service.service_type.select" var="serviceTypeSelect" />
                        <form:option value="" label="${serviceTypeSelect}" />
                        <c:forEach items="${serviceTypes}" var="type">
                            <form:option label="${type}" value="${type.id}" duration="${type.duration}" />
                        </c:forEach>
                    </form:select>
                    <form:errors path="services[${status.index}].serviceType" cssClass="error fs-xs" />
                </div>
                <!-- .form-group (SERVICE TYPE) -->

                <div class="col-sm-3 form-group">
                    <div class="row">
                        <a id="role-dropdown" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"
                            aria-expanded="false"><spring:message code="schedule.service.roles.dropdown" /> <b
                            class="caret"></b></a>
                        <ul class="dropdown-menu role-list" role="menu" aria-labelledby="role-dropdown">
                            <c:forEach items="${serviceForm.existingRoles}" var="branchRole">
                                <li class="role-item"><form:checkbox path="services[${status.index}].roles"
                                        value="${branchRole.key}" label="${branchRole.value}" /></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="row service-roles-summary">
                        <form:errors path="services[${status.index}].roles" cssClass="error fs-xs" />
                        <ul>
                            <c:forEach var="item" items="${service.roles}">
                                <li id="${item}" class="fs-xs">${serviceForm.existingRoles[item]}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- .form-group (ROLES) -->

                <div class="col-sm-3 form-group">
                    <c:choose>
                        <c:when test="${service.repeated}">
                            <button type="button" class="btn btn-default">
                                <spring:message code="schedule.service.repetition.remove" />
                            </button>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <spring:message code="schedule.service.tooltip.repetition_type" var="repTypeTip" />
                                <form:select path="services[${status.index}].repetitionType" id="repetitionType"
                                    tabindex="4" class="form-control" data-toggle="tooltip" data-placement="right"
                                    title="${repTypeTip}">
                                    <c:forEach items="${serviceForm.existingRepetitionTypes}" var="rType">
                                        <form:option value="${rType}">
                                            <spring:message code="${rType.msgCode}" />
                                        </form:option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="services[${status.index}].repetitionType" cssClass="error fs-xs" />
                            </div>
                            <div class="row">
                                <spring:message code="schedule.service.tooltip.repetition_date" var="repDateTip" />
                                <form:input path="services[${status.index}].repetitionDate" id="repetitionDate"
                                    type="date" tabindex="5" class="form-control" value="${service.repetitionDate}"
                                    data-toggle="tooltip" data-placement="right" title="${repDateTip}" />
                                <form:errors path="services[${status.index}].repetitionDate" cssClass="error" />
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
                <!-- .form-group (REPETITION) -->

            </div>
            <!-- .service-row -->
        </c:forEach>

        <div class="row service-row">
            <div class="col-sm-3 form-group">
                <div class="col-sm-4">
                    <span class="glyphicon glyphicon-time"></span>
                </div>
                <div class="col-sm-8">
                    <a href="javascript:newService()"><span class="service-add glyphicon glyphicon-plus"></span></a>
                </div>
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

<script type="text/javascript" src="/js/forms.js"></script>
<script type="text/javascript" src="/js/moment.js"></script>
<script type="text/javascript" src="/js/services.js"></script>