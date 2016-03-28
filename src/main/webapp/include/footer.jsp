<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<footer>
    <div class="mastfoot">

        <spring:message code="footer.author.label" var="footerLabel" />
        <p class="text-muted">${footerLabel}
            <a href="https://twitter.com/malkomich">@malkomich</a>.
        </p>

    </div>
</footer>