package am.neovision

class Address {

    Country country
    String state
    String city
    String fullAddress

    static belongsTo = [user:User]


    static mapping = {
        version false
    }

    static constraints = {
        country(nullable: true)
        user(nullable: true)
        state()
        city()
        fullAddress()
    }
}
