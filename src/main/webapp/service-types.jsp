<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="service-type-list">
    <c:forEach items="${serviceTypes}" var="serviceType">

        <div class="select-menu-item service-type-item">
            <div class="row">
                <div class="col-xs-8">
                    <a href="#">
                        <div class="select-menu-item-text">
                            <div class="row">
                                <span class="name" data-toggle="tooltip" data-placement="left"
                                    title="${serviceType.description}">${serviceType.name}</span>
                            </div>
                            <div class="row tip-text">
                                <span class="info"> <span class="bookings"><spring:message
                                            code="menu.filters.service-type.tip.bookings"
                                            arguments="${serviceType.maxBookingsPerRole}" argumentSeparator="¬" /> </span><span
                                    class="duration"><spring:message
                                            code="menu.filters.service-type.tip.duration"
                                            arguments="${serviceType.duration}" argumentSeparator="¬" /></span>
                                </span>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-xs-2">
                    <a href="#" sTypeId="${serviceType.id}" class="glyphicon glyphicon-pencil service-type_modify"></a>
                </div>
                <div class="col-xs-2">
                    <a href="#" sTypeId="${serviceType.id}" class="glyphicon glyphicon-remove service-type_delete"></a>
                </div>
            </div>
        </div>

    </c:forEach>

    <a href="#" class="select-menu-item service-type_add">
        <div class="select-menu-item-text">
            <span class="glyphicon glyphicon-plus"></span> <span><spring:message
                    code="menu.filters.service-type.add" /></span>
        </div>
    </a>

</div>