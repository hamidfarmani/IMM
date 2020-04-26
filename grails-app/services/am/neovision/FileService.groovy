package am.neovision

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

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
                domainInstance."$col" = row.get(col)
            }
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

    def getAllFromMongo(){
        String className = "ObjectStorage"
        Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
        String packageName = inputDomainClass.getPackage().getName() + '.'
        def columnNames = grailsApplication.getDomainClass(packageName+className).persistentProperties.collect { it.name }
        def listOfObjectStorages = ObjectStorage.getAll()
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
}
