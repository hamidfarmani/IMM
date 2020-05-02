<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>NEOVISION</title>
		<asset:javascript src="application.js"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="stylesheet" href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.4/css/select2.min.css" rel="stylesheet" />
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.4/js/select2.min.js"></script>
		<link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.16.0/css/mdb.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">

	</head>
	<body>
		<div class="container center-wrapper">

			<g:form  controller="file" action="getItemsFromMongo">
					<select id="MyDomains" name="checkedDomains" class="js-select2" multiple="multiple">
						<g:each in="${allDomains}" status="i" var="domain">
							<option>${domain}</option>
						</g:each>
					</select>

				<br><br>
				<button class="custom-button submit">Download MongoDB content (Json)</button>
			</g:form>

	<g:form controller="file" action="uploadJsonFileToMySQL" enctype="multipart/form-data">
		<div class="file-field">
			<a class="btn-floating btn-lg grey lighten-1 mt-0 float-left">
				<i class="fas fa-paperclip" aria-hidden="true"></i>
				<input type="file" name="jsonfile">

			</a>
		</div>
		<button type="submit" class="custom-button submit">Import to mysql</button>
		<br><br>

		<div>
			<g:if test="${flash.message}">
				<div class="message_info" role="status">${flash.message}</div>
			</g:if>
			<g:if test="${flash.warning}">
				<div class="message_error">${flash.warning}</div>
			</g:if>
		</div>
	</g:form>

	</div>
<script>

</script>
	</body>
</html>
