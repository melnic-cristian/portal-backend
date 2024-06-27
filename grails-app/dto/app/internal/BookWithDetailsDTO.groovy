package app.internal

import app.Book

import java.sql.Timestamp

class BookWithDetailsDTO {
    Long id
    String title
    String isbn
    String imageLink
    Date publicDate
    GenreDTO genre
    List<AuthorDTO> authors = []

    BookWithDetailsDTO(Book book, List<AuthorDTO> authors) {
        this.id = book.id
        this.title = book.title
        this.isbn = book.isbn
        this.imageLink = book.imageLink
        this.publicDate = book.publicDate
        this.genre = new GenreDTO(book.genre)
        this.authors = authors
    }
}
