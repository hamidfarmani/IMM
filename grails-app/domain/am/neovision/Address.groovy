package am.neovision

class Address {

    Country country
    String state
    String city
    String fullAddress

    static mapping = {
        version false
    }

    static constraints = {
        country(nullable: true)
        state()
        city()
        fullAddress()
    }
}
