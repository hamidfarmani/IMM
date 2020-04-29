package am.neovision

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.http.HttpStatus

class FileController {

    def fileService
    def mockService

    def index(){
        def allDomains = fileService.getAllDomains()
        allDomains -= ['ObjectStorage']
        render(view: '/index', model: [allDomains: allDomains])
    }

    def uploadJsonFileToMySQL(){
        def input = request.getFile("jsonfile").inputStream.text
        if(input != "") {
            fileService.saveInMySQL(input)
        }else{
            flash.message = message(code: "file.empty", status: HttpStatus.BAD_REQUEST)
        }
        redirect view: '../index'
    }

    def saveToMongo(){
        mockService.fillTempObject()
        redirect view:'../index'
    }

    def getItemsFromMongo(){
        def selectedDomains = request.getParameterValues("checkedDomains")
        def responseObject = fileService.getAllFromMongo(selectedDomains)
        def contentType = "application/octet-stream"
        def filename = "export.json"
        response.setHeader("Content-Disposition", "attachment;filename=${filename}")
        render(contentType: contentType, text: responseObject)
    }
}
