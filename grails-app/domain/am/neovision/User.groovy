package am.neovision

class User {

    String fullname
    Date birthdate
    Double height
    Integer numberOfChild

    static hasMany = [roles: Role]

    static mapping = {
        version false
    }

    static constraints = {
        fullname()
        birthdate()
        height()
        numberOfChild()
        roles(nullable: true)
    }
}
