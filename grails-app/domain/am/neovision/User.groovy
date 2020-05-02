package am.neovision

class User {

    String fullname
    Double height
    Integer numberOfChild
    Address address

    static hasMany = [roles: Role]

    static mapping = {
        version false
    }

    static constraints = {
        fullname()
        height(nullable: true)
        numberOfChild()
        roles(nullable: true)
        address(nullable: true)
    }
}
