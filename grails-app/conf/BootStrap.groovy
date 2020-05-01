import am.neovision.ObjectStorage

class BootStrap {

    def mockService
    def init = { servletContext ->
        ObjectStorage.collection.remove([ _id:[ $gt:0 ] ])
        mockService.fillTempObject()
    }
    def destroy = {
    }
}
