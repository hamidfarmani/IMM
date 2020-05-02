import am.neovision.Collection_Global

class BootStrap {

    def mockService
    def init = { servletContext ->
        Collection_Global.collection.remove([_id:[$gt:0 ] ])
        mockService.fillTempObject()
    }
    def destroy = {
    }
}
