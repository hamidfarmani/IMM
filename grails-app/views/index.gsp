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
%{--	<g:link controller="file" action="getItemsFromMySQL">--}%
%{--		<button>Export/Download from MySQL</button>--}%
%{--	</g:link>--}%
%{--	<g:link controller="file" action="importjson">--}%
%{--		<button>Import/Upload Json file and execute it in Mysql database</button>--}%
%{--	</g:link>--}%

	<g:form controller="file" action="uploadFile" enctype="multipart/form-data">
		<input name="jsonfile" type="file" />
		<button type="submit" class="custom-button submit">Import to mysql</button>
	</g:form>
	</div>
	</body>
</html>
