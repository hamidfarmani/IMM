package am.neovision


import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class MockService {
    def fileService

    /**
     * Filling the MongoDB with mock data.
     * @param Nothing
     * @return Nothing
     */
    def fillTempObject() {

        for (int i = 0; i < 5; i++) {
            JSONObject country = new JSONObject()
            country.put("name", "name $i")
            country.put("continent","continent $i")
            Collection_Global collection_Global = new Collection_Global()
            collection_Global.setDomainName("Country")
            collection_Global.setDataJsonValues(country.toString())
            fileService.saveInMongo(collection_Global)
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

            Collection_Global collection_Global = new Collection_Global()
            collection_Global.setDomainName("User")
            collection_Global.setDataJsonValues(user.toString())
            fileService.saveInMongo(collection_Global)
        }

        for (int i = 0; i < 5; i++) {
            JSONObject author = new JSONObject()
            author.put("firstname", "first $i")
            author.put("lastname","last $i")
            JSONArray books = new JSONArray()
            JSONObject book = new JSONObject()

            if(i%2==0){
                book.put("title" , "first")
                book.put("publishedDate" , "2020-04-23 21:02")
            }else{
                book.put("title" , "new book")
                book.put("publishedDate" , "2020-01-23 06:39")
            }
            books.add(book)
            author.put("books",books)

            Collection_Global collection_Global = new Collection_Global()
            collection_Global.setDomainName("Author")
            collection_Global.setDataJsonValues(author.toString())
            fileService.saveInMongo(collection_Global)
        }



        JSONObject newRole = new JSONObject()
        newRole.put("name" , "ADMIN")
        newRole.put("name" , "USER")

        Collection_Global os = new Collection_Global()
        os.setDomainName("Role")
        os.setDataJsonValues(newRole.toString())
        fileService.saveInMongo(os)
    }
}
