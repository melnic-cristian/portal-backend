package app

class Book {

    Long id
    String title
    String isbn
    String imageLink
    Date publicDate
    Genre genre
    Date dateCreated
    Date lastUpdated

    static hasMany = [bookAuthors: BookAuthor]

    static transients = ['version']

    static constraints = {
        title blank: false, nullable: false
        isbn blank: false, nullable: false, maxSize: 13
        imageLink nullable: true
        publicDate nullable: false
        genre nullable: false
    }

    static mapping = {
        id generator: 'identity'
        title column: 'title', nullable: false
        isbn column: 'isbn', nullable: false, length: 13
        imageLink column: 'image_link', nullable: true
        publicDate column: 'public_date', nullable: false
        genre column: 'genre_id', nullable: false
        bookAuthors cascade: 'all-delete-orphan'
        version false
        dateCreated column: 'created_at'
        lastUpdated column: 'updated_at'
    }
}
