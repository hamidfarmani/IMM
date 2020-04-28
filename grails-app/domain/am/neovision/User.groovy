package am.neovision

class User {

    String fullname
    Double height
    Integer numberOfChild

    static hasMany = [roles: Role]

    static mapping = {
        version false
    }

    static constraints = {
        fullname()
        height()
        numberOfChild()
        roles(nullable: true)
    }
}
