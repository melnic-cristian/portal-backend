package app.internal

import app.Author

class AuthorDTO {
    Long id
    String firstName
    String lastName

    AuthorDTO(Author author) {
        this.id = author.id
        this.firstName = author.firstName
        this.lastName = author.lastName
    }
}
