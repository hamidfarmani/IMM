package am.neovision

class MockService {
    def fileService

    def fillTempObject() {
        for (int i = 0; i < 10; i++) {
            ObjectStorage objectStorage = new ObjectStorage()
            objectStorage.setDomainName("Country")
            objectStorage.setDataJsonValues('{"Country":[{"continent":"EU","name":"Country'+i+'"}]}')
            fileService.saveInMongo(objectStorage)
        }

        for (int i = 0; i < 10; i++) {
            ObjectStorage objectStorage = new ObjectStorage()
            objectStorage.setDomainName("Book")
            objectStorage.setDataJsonValues('{"Book":[{"author":"author'+i+'","title":"title'+i+'"}]}')
            fileService.saveInMongo(objectStorage)
        }
    }
}
