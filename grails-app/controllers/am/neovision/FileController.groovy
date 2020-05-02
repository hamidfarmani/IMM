package am.neovision

import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import groovy.json.JsonBuilder
import org.apache.commons.io.FileUtils
import org.codehaus.groovy.grails.web.json.JSONException
import org.springframework.http.HttpStatus

import java.text.Format
import java.text.SimpleDateFormat

class FileController {

    def fileService
    def mockService

    /**
     * Rendering the index.gsp file with all domains in the drop-down.
     * @param Nothing
     * @return Array of domain's name in params.allDomains
     */
    def index(){
        def allDomains = fileService.getAllDomains()
        allDomains -= ['Collection_Global']
        render(view: '/index', model: [allDomains: allDomains])
    }

    /**
     * Get the json file from input and import it into MySQL database. It will show proper error messages in wherever needed.
     * @param jsonfile from request.
     * @return proper message in flash.
     */
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
                    flash.message = message(code: "file.imported", status: HttpStatus.OK)
                }
            }
        }catch(JSONException e){
            flash.message = message(code: "file.invalid.json", status: HttpStatus.BAD_REQUEST)
        }catch(JsonSyntaxException e){
            flash.message =  message(message:e.getMessage(),status: HttpStatus.BAD_REQUEST)
        }catch(NullPointerException e){
            flash.message = message(code: "file.invalid.domain", status: HttpStatus.BAD_REQUEST)
        }catch(IOException e){
            flash.message = message(message:e.getMessage(),status: HttpStatus.BAD_REQUEST)
        }catch(Exception e){
            flash.message = message(message:e.getMessage(),status: HttpStatus.BAD_REQUEST)
        } finally {
            redirect view: '../index'
        }
    }

    /**
     * Used to fill the MongoDB with mock data.
     * @param Nothing
     * @return Nothing
     */
    def saveToMongo(){
        mockService.fillTempObject()
        redirect view:'../index'
    }

    /**
     * Prepare a json file of selected domains and put it in Export folder. Automatically start download on-fly.
     * @param params.checkedDomains as selected domains in index.gsp
     * @return export.json file prettified with selected domains value
     */
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
