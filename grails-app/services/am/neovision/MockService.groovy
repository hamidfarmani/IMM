package am.neovision

class MockService {
    def fileService

    def fillTempObject() {
        for (int i = 0; i < 10; i++) {
            Book book = new Book()
            book.setTitle("title $i")
            book.setAuthor("author $i")
            fileService.saveInMongo(book)
        }

        for (int i = 0; i < 10; i++) {
            Country country = new Country()
            country.setName("name $i")
            country.setContinent("continent $i")
            fileService.saveInMongo(country)
        }
    }
}
