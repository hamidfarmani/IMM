<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Import Json File</title>
</head>

<body>
<g:form controller="file" action="uploadFile" enctype="multipart/form-data">
    <input name="jsonfile" type="file" />
    <button type="submit" class="custom-button edit">Import to mysql</button>
</g:form>

<g:form controller="file" action="uploadJsonFile" enctype="multipart/form-data">
    <input name="mongojsonfile" type="file" />
    <button type="submit" class="custom-button edit">Import to MongoDB</button>
</g:form>
</body>
</html>