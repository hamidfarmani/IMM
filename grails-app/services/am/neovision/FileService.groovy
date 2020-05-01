package am.neovision


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONException
import org.codehaus.groovy.grails.web.json.JSONObject


@Transactional
class FileService {

    def grailsApplication

    def saveInMySQL(def input) {
        GsonBuilder gsonBuilder = new GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm")
        Gson gson = gsonBuilder.create()
        try{
            JSONObject domainJson = new JSONObject(input)
            for (int i = 0; i < domainJson.keys().size(); i++) {
                String className = domainJson.keys().getAt(i)
                Class inputDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz

                domainJson.get(className).each { a ->
                    def instanceOfDomain = gson.fromJson(a.toString(), inputDomainClass)
                    for (def property : instanceOfDomain.properties) {
                        def val = property.getValue()
                        if (val instanceof LinkedHashSet) {
                            instanceOfDomain."${property.getKey()}".each { child ->
                                instanceOfDomain.addTo(property.getKey(), child)
                            }
                        }
                    }
                    instanceOfDomain.save()
                }
            }
        }catch(JSONException e) {
            println(e.getMessage())
            throw new JSONException(e)
        }catch(NullPointerException e) {
            println(e.getMessage())
            throw new NullPointerException()
        }
    }


    def saveInMongo(ObjectStorage objectStorage){
        objectStorage.save()
    }

    def getAllFromMongo(def selectedDomains){
        selectedDomains = selectedDomains?:getAllDomains()-['ObjectStorage']
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

}
