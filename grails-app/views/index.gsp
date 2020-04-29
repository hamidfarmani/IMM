<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>NEOVISION</title>
		<asset:javascript src="application.js"/>
		<link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet" />
		<script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>


	</head>
	<body>
		<div class="container center-wrapper">




			<g:form  controller="file" action="getItemsFromMongo">

				<label class="col-md-4 " for="MyDomains">
					Select the domains you want to download:
					<select id="MyDomains" name="checkedDomains" class="domains form-control" multiple="multiple" >
						<g:each in="${allDomains}" status="i" var="domain">
							<option>${domain}</option>
						</g:each>
					</select>
				</label>
				<br><br>
				<button class="custom-button submit">Download MongoDB content (Json)</button>
			</g:form>

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
<script>
	$(document).ready(function() {
		$('.domains').select2();
	});
</script>
	</body>
</html>
