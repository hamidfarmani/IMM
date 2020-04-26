<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>NEOVISION</title>

	</head>
	<body>
	<div class="container center-wrapper">

	<g:link controller="file" action="getItemsFromMongo">
		<button class="custom-button submit">Download MongoDB content (Json)</button>
	</g:link>

	<g:form controller="file" action="uploadJsonFileToMySQL" enctype="multipart/form-data">
		<input name="jsonfile" type="file" />
		<button type="submit" class="custom-button submit">Import to mysql</button>
	</g:form>
		<div class="message_error">
			<g:if test="${flash.message}">
				${flash.message}
			</g:if>
		</div>
	</div>
	</body>
</html>
