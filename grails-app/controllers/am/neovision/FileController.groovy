package am.neovision

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class FileController {

    def fileService

    def index() { }

    def importjson(){}

    def uploadFile(){
        def input = request.getFile("jsonfile").inputStream.text
        fileService.saveInMySQL(input)
        render view: '../index'
    }

    def uploadJsonFile(){
        def input = request.getFile("mongojsonfile").inputStream.text
        fileService.saveInMongo(input)
        render view: '../index'
    }

    def saveToMongo(){
        ObjectStorage objectStorage = new ObjectStorage()
        fileService.saveInMongo(objectStorage)
        render view:'../index'
    }

    def getItemsFromMongo(){
        def responseObject = fileService.getAllFromMongo()
        def contentType = "application/octet-stream"
        def filename = "export.json"
        response.setHeader("Content-Disposition", "attachment;filename=${filename}")
        render(contentType: contentType, text: responseObject)
    }

    def getItemsFromMySQL(){
        def responseObject = fileService.getAllFromMySQL()
        def contentType = "application/octet-stream"
        def filename = "export.json"
        response.setHeader("Content-Disposition", "attachment;filename=${filename}")
        render(contentType: contentType, text: responseObject)
    }
}
