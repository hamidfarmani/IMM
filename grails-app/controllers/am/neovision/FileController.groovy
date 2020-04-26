package am.neovision

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class FileController {

    def fileService
    def mockService

    def index() { }

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
