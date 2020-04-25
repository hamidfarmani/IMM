package am.neovision

class Book {

    String author
    String title

    static constraints = {
        author()
        title()
    }
}
