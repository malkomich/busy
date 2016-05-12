<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<form:form class="form-signin service-form" id="serviceForm" action="javascript:saveServices()"
    modelAttribute="serviceTypeForm" method="POST">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">
            <joda:format value="${serviceForm.date}" pattern="dd/MM/yyyy" />
        </h4>
    </div>

    <div class="modal-body">

        <c:forEach items="${serviceForm.services}" var="service">
            <jsp:include page="service-form-row.jsp" />
        </c:forEach>

        <div class="row service-row">
            <div class="col-sm-1 form-group">
                <span class="glyphicon glyphicon-time"></span>
            </div>

            <div class="col-sm-1 form-group">
                <span class="service-add glyphicon glyphicon-plus"></span>
            </div>

        </div>

    </div>
    <!-- .modal-body -->

    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">
            <spring:message code="modal.close" />
        </button>
        <button id="submit" type="submit" class="btn btn-primary" tabindex="5">
            <spring:message code="form.save" />
        </button>
    </div>
</form:form>

<script type="text/javascript" src="/js/signup.js"></script>