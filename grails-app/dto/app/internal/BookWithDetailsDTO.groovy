package app.internal

import app.Book

class BookWithDetailsDTO {
    Long id
    String title
    String isbn
    String imageLink
    String summary
    Date publicDate
    GenreDTO genre
    List<AuthorDTO> authors = []

    BookWithDetailsDTO(Book book, List<AuthorDTO> authors) {
        this.id = book.id
        this.title = book.title
        this.isbn = book.isbn
        this.imageLink = book.imageLink
        this.summary = book.summary
        this.publicDate = book.publicDate
        this.genre = book.genre != null ? new GenreDTO(book.genre) : null
        this.authors = authors
    }
}
