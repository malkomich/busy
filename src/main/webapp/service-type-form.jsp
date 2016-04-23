<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:form class="form-signin service-type-form" id="serviceTypeForm" action="javascript:saveServiceType()"
    modelAttribute="serviceTypeForm" method="POST">
    <div class="row">
        <div class="col-sm-12 form-group">
            <spring:message code="name.placeholder" var="namePlaceHolder" />
            <form:input path="name" id="name" tabindex="1" class="form-control mandatory"
                placeholder="${namePlaceHolder}" />
            <span class="mandatory-mark glyphicon glyphicon-asterisk error"></span>
            <form:errors path="name" cssClass="error" />
        </div>
    </div>

    <div class="row">
        <div class="form-group col-sm-12">
            <spring:message code="description.placeholder" var="descriptionPlaceHolder" />
            <form:input path="description" id="description" tabindex="2" class="form-control"
                placeholder="${descriptionPlaceHolder}" />
        </div>
    </div>


    <div class="row">
        <div class="col-sm-6 form-group">
            <spring:message code="bookings_per_role.placeholder" var="bookingsPlaceHolder" />
            <label for="maxBookingsPerRole">${bookingsPlaceHolder}:</label>
            <form:input path="maxBookingsPerRole" id="maxBookingsPerRole" tabindex="3" type="number" min="1" step="1"
                class="form-control" placeholder="${bookingsPlaceHolder}" />
        </div>
        <div class="col-sm-6 form-group">
            <spring:message code="duration.placeholder" var="durationPlaceHolder" />
            <label for="duration">${durationPlaceHolder}:</label>
            <form:input path="duration" id="duration" tabindex="4" type="number" min="5" step="1" class="form-control"
                placeholder="${durationPlaceHolder}" />
        </div>
    </div>

    <button type="button" class="btn btn-default" data-dismiss="modal">
        <spring:message code="modal.close" />
    </button>
    <button id="submit" type="submit" class="btn btn-primary" tabindex="5">
        <spring:message code="form.save" />
    </button>

    <form:input path="id" hidden="true" />
</form:form>

<script type="text/javascript" src="/js/signup.js"></script>