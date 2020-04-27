package am.neovision

class Role {

    String name

    static mapping = {
        version false
    }

    static constraints = {
        name()
    }
}
