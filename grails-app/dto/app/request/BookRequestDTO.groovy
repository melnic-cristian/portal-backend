package app.request

import grails.validation.Validateable

class BookRequestDTO implements Validateable {
    String title
    String isbn
    String imageLink
    String summary
    Date publicDate
    Long genreId
    List<Long> authorIds

    static constraints = {
        title nullable: false, size: 2..255
        summary nullable: false, size: 1..5000
        isbn nullable: true, size: 10..13, matches: /^\d{10}(\d{3})?$/
        imageLink nullable: true, url: true, size: 1..5000
        publicDate nullable: true
        genreId nullable: true
        authorIds validator: { val, obj ->
            if (!val || val.isEmpty() || !val.every { it instanceof Long }) {
                return ['invalidAuthors']
            }
        }
    }
}
