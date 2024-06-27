package app.internal

import app.Author;

class AuthorWithDetailsDTO {
    Long id
    String firstName
    String lastName
    String biography

    AuthorWithDetailsDTO(Author author) {
        this.id = author.id
        this.firstName = author.firstName
        this.lastName = author.lastName
        this.biography = author.biography
    }
}
