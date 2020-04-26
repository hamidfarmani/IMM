package am.neovision

class MockService {
    def fileService

    def fillTempObject() {
//        for (int i = 0; i < 10; i++) {
            ObjectStorage objectStorage = new ObjectStorage()
            Book book = new Book()
            book.setTitle("title 1")
            book.setAuthor("author 1")
            fileService.saveInMongo(book)
//        }

//        for (int i = 0; i < 10; i++) {
//            ObjectStorage objectStorage = new ObjectStorage()
//            objectStorage.setDomainName("Book")
//            objectStorage.setDataJsonValues('{"Book":[{"author":"author'+i+'","title":"title'+i+'"}]}')
        Country country = new Country()
        country.setName("name")
        country.setContinent("continent")
        fileService.saveInMongo(country)
//        }
    }
}
