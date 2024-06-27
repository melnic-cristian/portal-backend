package app.request

import grails.validation.Validateable

class GenreRequestDTO implements Validateable {
    String name
    String description

    static constraints = {
        name blank: false, size: 1..255
        description blank: false, maxSize: 5000
    }
}
