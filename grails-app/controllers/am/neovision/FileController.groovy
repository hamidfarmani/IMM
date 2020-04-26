package am.neovision

import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.http.HttpStatus

class FileController {

    def fileService
    def mockService

    def uploadJsonFileToMySQL(){
        def input = request.getFile("jsonfile").inputStream.text
        if(input != "") {
            fileService.saveInMySQL(inputJsonObject)
        }else{
            flash.message = message(code: "file.empty", status: HttpStatus.BAD_REQUEST)
        }
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
