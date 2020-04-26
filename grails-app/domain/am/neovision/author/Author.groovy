package am.neovision.author

import am.neovision.Book

class Author {
    String firstname
    String lastname
    static hasMany = [books: Book]

    static mapping = {
        version false
    }

    static constraints = {
        books(nullable: true)
        firstname()
        lastname()
    }

}
