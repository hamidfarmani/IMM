package am.neovision

import am.neovision.author.Author

class Book {

    String title
    Author author

    static mapping = {
        version false
    }

    static constraints = {
        author(nullable: true)
        title()
    }
}
