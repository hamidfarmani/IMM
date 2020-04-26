package am.neovision

class FileController {

    def fileService
    def mockService

    def uploadJsonFileToMySQL(){
        def input = request.getFile("jsonfile").inputStream.text
        fileService.saveInMySQL(input)
        render view: '../index'
    }

    def saveToMongo(){
        mockService.fillTempObject()
        render view:'../index'
    }

    def getItemsFromMongo(){
        def responseObject = fileService.getAllFromMongo()
        def contentType = "application/octet-stream"
        def filename = "export.json"
        response.setHeader("Content-Disposition", "attachment;filename=${filename}")
        render(contentType: contentType, text: responseObject)
    }
}
