package app.internal

import app.Book

class BookDTO {
    Long id
    String title

    BookDTO(Book book) {
        this.id = book.id
        this.title = book.title
    }
}
