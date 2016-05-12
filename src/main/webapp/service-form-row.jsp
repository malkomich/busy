<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row service-row">
    <div class="col-sm-1 form-group">
        <span class="glyphicon glyphicon-time"></span>
    </div>

    <div class="col-sm-2 form-group">
        <div class="row">
            <spring:message code="schedule.service.start_time.placeholder" var="startTimePlaceholder" />
            <form:input path="startTime" id="startTime" type="time" tabindex="1" class="form-control"
                placeholder="${startTimePlaceholder}" value="${service.startTime}" />
        </div>
        <div class="row service-endTime"></div>
    </div>

    <div class="col-sm-2 form-group">
        <form:select path="serviceType" id="serviceType" tabindex="2" class="form-control">
            <spring:message code="schedule.service.service_type.select" var="serviceTypeSelect" />
            <form:option value="" label="${serviceTypeSelect}" />
            <form:options items="${serviceTypes}" itemValue="${service.serviceType}" />
        </form:select>
        <form:errors path="serviceType" cssClass="error" />
    </div>

    <div class="col-sm-2 form-group">
        <div class="row">
            <ul class="dropdown-menu role-list">
                <li><spring:message code="schedule.service.roles.dropdown" /></li>
                <c:forEach items="${serviceForm.roleList}" var="branchRole">
                    <li class="role-item">
                        <form:checkbox path="roles" value="${branchRole}" />
                    </li>
                </c:forEach>
            </ul>
            <form:errors path="roles" cssClass="error" />
        </div>
        <div class="row service-roles-summary"></div>
    </div>

    <div class="col-sm-2 form-group">
        <form:select path="repetitionType" id="repetitionType" tabindex="2" class="form-control"
            items="${repetitionTypes}" itemValue="${service.repetitionType}" />
    </div>

    <div class="col-sm-2 form-group">
        <form:input path="repetitionDate" id="repetitionDate" type="date" tabindex="2" class="form-control"
            value="<joda:format value="${service.repetitionDate}" pattern="dd/MM/yyyy" />" />
        <form:errors path="repetitionDate" cssClass="error" />
    </div>
</div>
<!-- .service-row -->