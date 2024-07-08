package app

class Author {

    Long id
    String firstName
    String lastName
    String biography
    Date dateCreated
    Date lastUpdated

    static hasMany = [bookAuthors: BookAuthor]

    static transients = ['version']

    static constraints = {
        firstName blank: false
        lastName blank: false
        biography blank: false, maxSize: 5000
    }

    static mapping = {
        id generator: 'identity'
        firstName column: 'first_name'
        lastName column: 'last_name'
        biography column: 'biography', length: 5000
        version false
        dateCreated column: 'created_at'
        lastUpdated column: 'updated_at'
    }
}
