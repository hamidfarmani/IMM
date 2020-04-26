package am.neovision

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONException
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class FileService {

    def grailsApplication


    public void loopThroughJson(Object input) throws JSONException {

        if (input instanceof JSONObject) {

            Iterator<?> keys = ((JSONObject) input).keys();

            while (keys.hasNext()) {

                String key = (String) keys.next();

                if (!(((JSONObject) input).get(key) instanceof JSONArray)){
                    if (((JSONObject) input).get(key) instanceof JSONObject) {
                        loopThroughJson(((JSONObject) input).get(key));
                    } else {
                        System.out.println(key + "=" + ((JSONObject) input).get(key));
                    }
            }else {
                loopThroughJson(new JSONArray(((JSONObject) input).get(key).toString()));
            }
            }
        }

        if (input instanceof JSONArray) {
            for (int i = 0; i < ((JSONArray) input).length(); i++) {
                JSONObject a = ((JSONArray) input).getJSONObject(i);
                loopThroughJson(a);
            }
        }

    }


    def saveInMySQL(def input){
        JSONObject inputJsonObject = new JSONObject(input)


//        String className = inputJsonObject.keys().getAt(0)
//        Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
//        String packageName = inputDomainClass.getPackage().getName() + '.'
//        def columnNames = grailsApplication.getDomainClass(packageName+className).persistentProperties.collect { it.name }
//
//        inputJsonObject.get(className).each { row ->
//
//            def domainInstance = inputDomainClass.newInstance()
//            columnNames.each{ col ->
//                def a = row.get(col)
//                if(a.class==null){
//
//                    def b = a as JSON
//                    println col + ' ' +  b
//                }
//                try{
//                    domainInstance."$col" = row.get(col)
//                }catch(Exception e){
//                    println e
//                }
//            }
//            println domainInstance.properties
//            domainInstance.save()
//        }
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
