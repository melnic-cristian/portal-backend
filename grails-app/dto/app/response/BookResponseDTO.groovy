package app.response

import app.internal.BookWithDetailsDTO
import org.springframework.http.HttpStatus

class BookResponseDTO {
    String message
    HttpStatus status
    BookWithDetailsDTO book

    BookResponseDTO(String message, HttpStatus status, BookWithDetailsDTO book = null) {
        this.message = message
        this.status = status
        this.book = book
    }
}