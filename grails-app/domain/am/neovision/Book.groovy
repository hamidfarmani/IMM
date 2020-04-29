package am.neovision

import am.neovision.author.Author

class Book {

    String title
    Date publishedDate
    static belongsTo = [author: Author]

    static mapping = {
        version false
    }

    static constraints = {
        author(nullable: true)
        title()
    }
}
