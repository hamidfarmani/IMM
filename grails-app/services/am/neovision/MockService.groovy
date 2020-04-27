package am.neovision

class MockService {
    def fileService

    def fillTempObject() {
        for (int i = 0; i < 5; i++) {
            Book book = new Book()
            book.setTitle("title $i")
            fileService.saveInMongo(book)
        }

        for (int i = 0; i < 5; i++) {
            Country country = new Country()
            country.setName("name $i")
            country.setContinent("continent $i")
            fileService.saveInMongo(country)
        }

        Role role = new Role(name: "ADMIN")
        role.save()
        Role role2 = new Role(name: "USER")
        role2.save()

        for (int i = 0; i < 5; i++) {
            User user = new User()
            user.setFullname("fullname $i")
            user.setBirthdate(new Date())
            user.setNumberOfChild(i)
            user.setHeight(140.6 + i)

            if(i%2==0){
                user.addTo("roles",role)
                user.addTo("roles",role2)
            }else{
                user.addTo("roles",role)
            }
            fileService.saveInMongo(user)
        }

        for (int i = 0; i < 5; i++) {
            Address address = new Address()
            address.setCity("City $i")
            address.setFullAddress("Full Address $i")
            address.setState("State $i")
            address.setCountry(Country.get(i+1))
            fileService.saveInMongo(address)
        }
    }
}
