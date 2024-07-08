package app

class Book {

    Long id
    String title
    String isbn
    String summary
    String imageLink
    Date publicDate
    Genre genre
    Date dateCreated
    Date lastUpdated

    static hasMany = [bookAuthors: BookAuthor]

    static transients = ['version']

    static constraints = {
        title nullable: false, blank: false, size: 2..255
        isbn nullable: true, blank: false, size: 10..13, matches: /^\d{10}(\d{3})?$/
        summary nullable: false, blank: false, size: 1..5000
        imageLink nullable: true, url: true, size: 1..5000
        publicDate nullable: true
        genre nullable: true
        bookAuthors cascade: 'all-delete-orphan'
    }

    static mapping = {
        id generator: 'identity'
        title column: 'title', length: 255
        isbn column: 'isbn', length: 13
        imageLink column: 'image_link', length: 5000
        publicDate column: 'public_date'
        genre column: 'genre_id'
        summary column: 'summary', length: 5000
        bookAuthors cascade: 'all-delete-orphan'
        version false
        dateCreated column: 'created_at'
        lastUpdated column: 'updated_at'
    }
}
