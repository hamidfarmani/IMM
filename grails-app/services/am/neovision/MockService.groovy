package am.neovision

import com.google.gson.Gson
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

import java.lang.reflect.Array

class MockService {
    def fileService

    def fillTempObject() {

        for (int i = 0; i < 5; i++) {
            JSONObject country = new JSONObject()
            country.put("name", "name $i")
            country.put("continent","continent $i")
            ObjectStorage objectStorage = new ObjectStorage()
            objectStorage.setDomainName("Country")
            objectStorage.setDataJsonValues(country.toString())
            fileService.saveInMongo(objectStorage)
        }


        for (int i = 0; i < 5; i++) {
            JSONObject user = new JSONObject()
            user.put("fullname", "fullname $i")
            user.put("numberOfChild",i)
            user.put("height",140.6 + i)

            JSONObject address = new JSONObject()
            address.put("city", "city $i")
            address.put("fullAddress","full address $i")
            address.put("state","state $i")
            JSONObject country = new JSONObject()
            country.put("name", "Armenia")
            country.put("continent","Asia")
            address.put("country",country)

            user.put("address", address)

            JSONArray roles = new JSONArray()
            JSONObject r = new JSONObject()
            if(i%2==0){
                r.put("name" , "ADMIN")
            }else{
                r.put("name" , "USER")
            }
            roles.add(r)
            user.put("roles",roles)

            ObjectStorage objectStorage = new ObjectStorage()
            objectStorage.setDomainName("User")
            objectStorage.setDataJsonValues(user.toString())
            fileService.saveInMongo(objectStorage)
        }

        for (int i = 0; i < 5; i++) {
            JSONObject author = new JSONObject()
            author.put("firstname", "first $i")
            author.put("lastname","last $i")
            JSONArray books = new JSONArray()
            JSONObject book = new JSONObject()

            if(i%2==0){
                book.put("title" , "first")
                book.put("title" , "second")
                book.put("title" , "third")
                book.put("title" , "fourth")
            }else{
                book.put("title" , "new book")
            }
            books.add(book)
            author.put("books",books)

            ObjectStorage objectStorage = new ObjectStorage()
            objectStorage.setDomainName("Author")
            objectStorage.setDataJsonValues(author.toString())
            fileService.saveInMongo(objectStorage)
        }



        JSONObject newRole = new JSONObject()
        newRole.put("name" , "ADMIN")
        newRole.put("name" , "USER")

        ObjectStorage os = new ObjectStorage()
        os.setDomainName("Role")
        os.setDataJsonValues(newRole.toString())
        fileService.saveInMongo(os)
    }
}
