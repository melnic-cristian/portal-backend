package app.response

import app.Author
import org.springframework.http.HttpStatus

class AuthorResponseDTO {

    String message
    Author author
    HttpStatus status

    AuthorResponseDTO(String message, HttpStatus status, Author author = null) {
        this.message = message
        this.status = status
        this.author = author
    }
}