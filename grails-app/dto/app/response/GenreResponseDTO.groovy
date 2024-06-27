package app.response

import app.Genre
import org.springframework.http.HttpStatus

class GenreResponseDTO {

    String message
    HttpStatus status
    Genre genre

    GenreResponseDTO(String message, HttpStatus status, Genre genre = null) {
        this.message = message
        this.status = status
        this.genre = genre
    }
}
