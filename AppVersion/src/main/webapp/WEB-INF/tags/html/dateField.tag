<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of corresponding property in bean object"%>
<%@ attribute name="label" required="true" rtexprvalue="true"
	description="Label appears in red color if input is considered as invalid after submission"%>
<%@ attribute name="require" required="false" rtexprvalue="true"
	description="This field is require or not"%>
<spring:message code="date_format_pattern_picker"
	var="dateFormatPatternPicker" />
<spring:bind path="${name}">
	<c:set var="cssGroup"
		value="control-group ${require ? 'require' : '' } ${status.error ? 'error' : '' }" />
	<div class="${cssGroup}" id="div_${name}">
		<form:label path="${name}" class="control-label">${label}
			<span class="star">*</span>
		</form:label>
		<div class="controls">
			<script type="text/javascript">
				$(function() {
					$('#${name}').datepicker({
						changeYear : true,
						dateFormat : '${dateFormatPatternPicker}'
					});
				});
			</script>
			<form:input path="${name}" />
			<div class="div-help-inline">
				<span class="help-inline">${status.errorMessage}</span>
			</div>
		</div>
	</div>
</spring:bind>