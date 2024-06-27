package app.request

import grails.validation.Validateable

import java.sql.Timestamp

class BookRequestDTO implements Validateable {
    String title
    String isbn
    String imageLink
    Date publicDate
    Long genreId
    List<Long> authorIds

    static constraints = {
        title blank: false, size: 1..255
        isbn blank: false, size: 10..13, validator: { val, obj ->
            if (!(val instanceof String) || !val.matches(/^\d{10}(\d{3})?$/)) {
                return ['isbnInvalid']
            }
        }
        imageLink nullable: true, url: true
        publicDate nullable: false
        genreId nullable: false
        authorIds validator: { val, obj ->
            if (!val || !val.every { it instanceof Long }) {
                return ['invalidAuthors']
            }
        }
    }
}
