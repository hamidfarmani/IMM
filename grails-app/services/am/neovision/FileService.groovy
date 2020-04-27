package am.neovision

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

@Transactional
class FileService {

    def grailsApplication

    def saveInMySQL(def input){
        JSONObject inputJsonObject = new JSONObject(input)
        String className = inputJsonObject.keys().getAt(0)
        Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
        String packageName = inputDomainClass.getPackage().getName() + '.'
        def columnNames = grailsApplication.getDomainClass(packageName+className).persistentProperties.collect { it.name }

        inputJsonObject.get(className).each { row ->
            def domainInstance = inputDomainClass.newInstance()
            columnNames.each{ col ->
                try{
                    def a = row.get(col)
                    if(a instanceof JSONArray){
                        a.each{ obj ->
                            domainInstance.addTo(col,obj)
                        }
                    } else if ((a instanceof String) && isValidDate(a)){
                        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                        Date date = format.parse(a)
                        println "\t\t ${date}"
                        domainInstance."$col" = date
                    }else {
                        domainInstance."$col" = row.get(col)
                    }
                }catch(Exception e){
                    println e
                }
            }
            println domainInstance.properties
            domainInstance.save()
        }
    }

    def saveInMongo(def domObj){
        ObjectStorage objectStorage = new ObjectStorage()
        objectStorage.setDomainName(domObj.class.getSimpleName())
        def domainJson =  domObj as JSON
        domainJson.setExcludes(domObj.class, ["class","id"])
        objectStorage.setDataJsonValues(domainJson.toString())
        objectStorage.save()
    }

    def getAllFromMongo(def selectedDomains){
        String className = "ObjectStorage"
        Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
        String packageName = inputDomainClass.getPackage().getName() + '.'
        def columnNames = grailsApplication.getDomainClass(packageName+className).persistentProperties.collect { it.name }
        selectedDomains = selectedDomains?:getAllDomains()
        def listOfObjectStorages = ObjectStorage.withCriteria {
            'in'("domainName",selectedDomains)
        }
        JSONArray array = new JSONArray()
        JSONObject responseObject = new JSONObject()
        listOfObjectStorages.each { objectStorage ->
            JSONObject temp = new JSONObject()
            columnNames.each { col ->
                temp.put(col, objectStorage.dbo.get(col))
            }
            array.put(temp)
        }
        responseObject.put(className,array)
        responseObject
    }

    def getAllDomains(){
        grailsApplication.getArtefacts("Domain")*.clazz.simpleName
    }

    def getPackage(){

    }

    public static boolean isValidDate(String d) {
        String regex = "(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d"
        Pattern pattern = Pattern.compile(regex)
        Matcher matcher = pattern.matcher((CharSequence)d)
        return matcher.matches()
    }
}
