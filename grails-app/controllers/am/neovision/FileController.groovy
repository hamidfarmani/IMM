package am.neovision

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONException
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
        try {
            if (input == "") {
                flash.message = message(code: "file.empty", status: HttpStatus.BAD_REQUEST)
            } else if (request.getFile("jsonfile").contentType != "application/json") {
                flash.message = message(code: "file.invalid.format", status: HttpStatus.BAD_REQUEST)
            } else {
                if (input != "") {
                    fileService.saveInMySQL(input)
                }
            }
        }catch(JSONException e){
            flash.message = message(code: "file.invalid.json", status: HttpStatus.BAD_REQUEST)
        } finally {
            redirect view: '../index'
        }
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
