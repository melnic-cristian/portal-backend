package app

class BookAuthor {

    Long id
    Book book
    Author author
    Date dateCreated
    Date lastUpdated

    static transients = ['version']

    static constraints = {
        book nullable: false
        author nullable: false
    }

    static belongsTo = [book: Book, author: Author]

    static mapping = {
        id generator: 'identity'
        book column: 'book_id', nullable: false
        author column: 'author_id', nullable: false
        version false
        dateCreated column: 'created_at'
        lastUpdated column: 'updated_at'
    }
}
