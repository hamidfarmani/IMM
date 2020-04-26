package am.neovision

import grails.transaction.Transactional
import groovy.json.JsonSlurper
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
//        JSONObject a = new JSONObject()
//        a.put("domainName", domObj.class.getSimpleName())
//        a.put("dataJsonValues", domObj.properties)
        ObjectStorage objectStorage = new ObjectStorage()
        objectStorage.setDomainName(domObj.class.getSimpleName())
        objectStorage.setDataJsonValues(domObj.properties.toString())

//        objectStorage.save()
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
        responseObject.put("ObjectStorage",array)
        responseObject
    }
}
