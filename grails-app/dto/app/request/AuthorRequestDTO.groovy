package app.request

import grails.validation.Validateable

class AuthorRequestDTO implements Validateable {
    String firstName
    String lastName
    String biography

    static constraints = {
        firstName blank: false, size: 2..100, validator: { val, obj ->
            if (!(val instanceof String) || !val.matches(/^[a-zA-Z]+$/)) {
                return ['invalidCharacters']
            }
        }
        lastName blank: false, size: 2..100, validator: { val, obj ->
            if (!(val instanceof String) || !val.matches(/^[a-zA-Z]+$/)) {
                return ['invalidCharacters']
            }
        }
        biography blank: false, maxSize: 5000, validator: { val, obj ->
            if (!(val instanceof String) || val.size() > 5000) {
                return ['invalidBiography']
            }
        }
    }
}
