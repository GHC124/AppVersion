<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="url" required="true" rtexprvalue="true"%>
<spring:url value="${url}" var="fullUrl"/>
<link href="${fullUrl}" rel="stylesheet" media="screen"  />
