package am.neovision

import groovy.json.JsonBuilder
import org.apache.commons.io.FileUtils
import org.codehaus.groovy.grails.web.json.JSONException
import org.springframework.http.HttpStatus

import java.text.Format
import java.text.SimpleDateFormat

class FileController {

    def fileService
    def mockService

    def index(){
        def allDomains = fileService.getAllDomains()
        allDomains -= ['Collection_Global']
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
        } catch(NullPointerException e){
            flash.message = message(code: "file.invalid.domain", status: HttpStatus.BAD_REQUEST)
        } finally {
            redirect view: '../index'
        }
    }

    def saveToMongo(){
        mockService.fillTempObject()
        redirect view:'../index'
    }

    def getItemsFromMongo(){
        Format formatter = new SimpleDateFormat("YYYY-MM-dd hh-mm-ss")
        def selectedDomains = request.getParameterValues("checkedDomains")
        def responseObject = fileService.getAllFromMongo(selectedDomains)
        def jsonFile = new JsonBuilder(responseObject).toPrettyString()
        String filename = "${selectedDomains?:'All'} - ${formatter.format(new Date())}.json"
        new File("./Export").mkdirs()
        new File("./Export/${filename}").write(jsonFile)
        def contentType = "application/octet-stream"
        response.setHeader("Content-Disposition", "attachment;filename=export.json")
        render(contentType: contentType, text: jsonFile)
    }
}
