package app

class Genre {

    Long id
    String name
    String description
    Date dateCreated
    Date lastUpdated

    static hasMany = [books: Book]

    static transients = ['version']

    static constraints = {
        name blank: false, nullable: false, size: 1..255
        description blank: false, nullable: false, maxSize: 5000
    }

    static mapping = {
        id generator: 'identity'
        name column: 'name', nullable: false
        description column: 'description', nullable: false, length: 5000
        version false
        books cascade: 'all-delete-orphan'
        dateCreated column: 'created_at'
        lastUpdated column: 'updated_at'
    }
}
