package am.neovision

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class FileService {

    def grailsApplication

    def serviceMethod() {

    }

    def saveInMySQL(def input){
        JSONObject a = new JSONObject(input)
        String packageName = "am.neovision."
        String className = a.keys().getAt(0)
        Class mySqlClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
        def names = grailsApplication.getDomainClass(packageName+className).persistentProperties.collect { it.name }

        a.get(className).each { row ->
            def domainInstance = mySqlClass.newInstance()
            names.each{ col ->
                domainInstance."$col" = row.get(col)
            }
            domainInstance.save()
        }
    }

    def saveInMongo(ObjectStorage os){
//        JSONObject a = new JSONObject(input)
//        def d = a.keys().getAt(0)
//        ObjectStorage os = new ObjectStorage()
//        os.setDomainName(d)
//        os.setDataJsonValues(a.get(d).toString())
        os.save()
    }

    def getAllFromMySQL(){
        String packageName = "am.neovision."
        String objStrg = "ObjectStorage"
        Class dom = grailsApplication.domainClasses.find { it.clazz.simpleName == objStrg }.clazz
        def domains = grailsApplication.getArtefacts("Domain")*.clazz
        JSONObject obj = new JSONObject()
        JSONArray domainArray = new JSONArray()
        domains.each{ d ->
            if(d!=dom) {
                JSONObject eachDomain = new JSONObject()
                JSONArray array = new JSONArray()
                println 'd: ' + d.simpleName
                def names = grailsApplication.getDomainClass(packageName+d.simpleName).persistentProperties.collect { it.name }
                def all = d.getAll()
                all.each { e ->
                    JSONObject temp = new JSONObject()
                    names.each{ col ->
                        temp.put(col,e.properties.get(col))
                    }
                    array.put(temp)
                }
                eachDomain.put(d.simpleName,array)
                domainArray.put(eachDomain)
            }
        }
        obj.put("AllDomains", domainArray)
        obj
    }

    def getAllFromMongo(){
        def a = ObjectStorage.getAll()
        JSONArray array = new JSONArray()
        JSONObject responseObject = new JSONObject()
        a.each {
            JSONObject temp = new JSONObject()
            temp.put("id",it.id)
            temp.put("domainName",it.domainName)
            temp.put("dataJsonValues",it.dataJsonValues)
            array.put(temp)
        }
        responseObject.put("ObjectStorage",array)
        responseObject
    }
}
