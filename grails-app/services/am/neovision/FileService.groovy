package am.neovision


import com.google.gson.Gson
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

    def saveInMySQL(def input) {
        Gson gson = new Gson()
        JSONObject domainJson = new JSONObject(input)
        for (int i = 0; i<domainJson.keys().size(); i++) {
            String className = domainJson.keys().getAt(i)
            Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz

            domainJson.get(className).each { a ->
                def instanceOfDomain = gson.fromJson(a.toString(), inputDomainClass)
                for(def property : instanceOfDomain.properties) {
                    def val = property.getValue()
                    if(val instanceof LinkedHashSet) {
                        instanceOfDomain."${property.getKey()}".each{ child->
                            instanceOfDomain.addTo(property.getKey(),child)
                        }
                    }
                }
                instanceOfDomain.save()
            }
        }
    }

    def parseThis(def input){
        JSONObject jj = new JSONObject(input)
        jj.get("User").each { row ->
            parsJson(row)
        }
    }

    def parsJson(def jsonInput){
        if (jsonInput == null) return null
        JSONArray jjkeys = jsonInput.names()
        for(int h=0;h<jsonInput.length();h++) {
            def key = jjkeys.get(h)
            def value = jsonInput.get(jjkeys.get(h))
            def typeofvalue = jsonInput.get(jjkeys.get(h)).class
            if(typeofvalue==null){
                //It should be parsed. It's JSONOBJECT. It may contain another one!
                parsJson(value)
            }else if (typeofvalue instanceof JSONArray){
                //It should be parsed. It's array of the previous condition. (type == null or JSONOBJECT).
                for(int i=0;i<value.get.length();i++){
                    parsJson(value.get(i))
                }
            }else{
                //easy case --> use the value, it's ready!
                print  value
            }
        }
    }

    def previousSolutionSaveInMySQL(def input){
        JSONObject inputJsonObject = new JSONObject(input)
        for (int i = 0; i<inputJsonObject.keys().size(); i++) {
            String className = inputJsonObject.keys().getAt(i)
            Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
            String packageName = inputDomainClass.getPackage().getName() + '.'
            def columnNames = grailsApplication.getDomainClass(packageName + className).persistentProperties.collect { it.name }

            inputJsonObject.get(className).each { row ->
                JSONObject jj = new JSONObject(row)
                def domainInstance = inputDomainClass.newInstance()
                columnNames.each { col ->
                    try {
                        def a = jj.get(col)
                        if (a instanceof JSONArray) {
                            a.each { obj ->
                                domainInstance.addTo(col, obj)
                            }
                        } else if ((a instanceof String) && isValidDate(a)) {
                            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                            Date date = format.parse(a)
                            domainInstance."$col" = date
                        } else {
                            domainInstance."$col" = jj.get(col)
                        }
                    } catch (Exception e) {
                        println e
                    }
                }
                println domainInstance.properties
                domainInstance.save()
            }
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
        selectedDomains = selectedDomains?:getAllDomains()
        def listOfObjectStorages = ObjectStorage.withCriteria {
            'in'("domainName",selectedDomains)
        }
        JSONObject responseObject = new JSONObject()
        for(def dom : selectedDomains) {
            JSONArray innerArray = new JSONArray()
            for (int i = 0; i < listOfObjectStorages.size(); i++) {
                if(listOfObjectStorages[i].dbo.get("domainName").equals(dom)) {
                    innerArray.put(listOfObjectStorages[i].dbo.get("dataJsonValues"))
                }
            }
            responseObject.put(dom,innerArray)
        }
        responseObject
    }

    def getAllDomains(){
        grailsApplication.getArtefacts("Domain")*.clazz.simpleName
    }

    def getPackage(){

    }

     static boolean isValidDate(String d) {
        String regex = "(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d"
        Pattern pattern = Pattern.compile(regex)
        Matcher matcher = pattern.matcher((CharSequence)d)
        return matcher.matches()
    }
}
