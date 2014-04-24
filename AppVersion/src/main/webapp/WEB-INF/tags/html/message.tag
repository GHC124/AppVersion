<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty message}">
	<div id="message" class="${message.type}">${message.message}</div>
</c:if>