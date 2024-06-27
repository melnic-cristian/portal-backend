package app

import app.internal.BookDTO
import app.internal.AuthorDTO
import app.internal.BookWithDetailsDTO
import app.request.BookRequestDTO
import app.response.BookResponseDTO
import grails.gorm.transactions.Transactional
import org.springframework.http.HttpStatus

class BookService {

    @Transactional
    BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {
        Genre genre = Genre.get(bookRequestDTO.genreId)
        if (!genre) {
            return new BookResponseDTO('Genre not found', HttpStatus.NOT_FOUND)
        }

        def book = new Book(
                title: bookRequestDTO.title,
                isbn: bookRequestDTO.isbn,
                publicDate: bookRequestDTO.publicDate,
                genre: genre,
                imageLink: bookRequestDTO.imageLink
        )

        if (book.save(flush: true)) {
            bookRequestDTO.authorIds.each { authorId ->
                Author author = Author.get(authorId)
                if (author) {
                    new BookAuthor(book: book, author: author).save(flush: true)
                }
            }
            book.refresh()
            def authorInfo = book.bookAuthors.collect { new AuthorDTO(it.author) }
            def bookWithDetailsDTO = new BookWithDetailsDTO(book, authorInfo)
            return new BookResponseDTO('Book created successfully', HttpStatus.CREATED, bookWithDetailsDTO)
        } else {
            return new BookResponseDTO('Error creating book', HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    BookResponseDTO updateBook(Long bookId, BookRequestDTO bookRequestDTO) {
        Book book = Book.get(bookId)
        if (!book) {
            return new BookResponseDTO('Book not found', HttpStatus.NOT_FOUND)
        }

        Genre genre = Genre.get(bookRequestDTO.genreId)
        if (!genre) {
            return new BookResponseDTO('Genre not found', HttpStatus.NOT_FOUND)
        }

        book.title = bookRequestDTO.title
        book.isbn = bookRequestDTO.isbn
        book.publicDate = bookRequestDTO.publicDate
        book.genre = genre
        book.imageLink = bookRequestDTO.imageLink

        if (book.save(flush: true)) {
            BookAuthor.findAllByBook(book).each { it.delete(flush: true) }
            bookRequestDTO.authorIds.each { authorId ->
                Author author = Author.get(authorId)
                if (author) {
                    new BookAuthor(book: book, author: author).save(flush: true)
                }
            }
            book.refresh()
            def authorInfo = book.bookAuthors.collect { new AuthorDTO(it.author) }
            def bookWithDetailsDTO = new BookWithDetailsDTO(book, authorInfo)
            return new BookResponseDTO('Book updated successfully', HttpStatus.OK, bookWithDetailsDTO)
        } else {
            return new BookResponseDTO('Error updating book', HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    BookResponseDTO deleteBook(Long id) {
        def book = Book.get(id)
        if (book) {
            book.delete(flush: true)
            return new BookResponseDTO('Book deleted successfully', HttpStatus.NO_CONTENT)
        } else {
            return new BookResponseDTO('Book not found to be deleted', HttpStatus.NOT_FOUND)
        }
    }

    def getTotalBooksCount() {
        Book.count()
    }

    List<BookWithDetailsDTO> getAllBooks(int max = 5, int offset = 0, int totalBooks) {
        if (offset >= totalBooks) return []

        max = Math.min(max, totalBooks - offset)

        List<Book> books = Book.createCriteria().list {
            maxResults(max)
            firstResult(offset)
            order("lastUpdated", "desc")
        }

        books.collect { book ->
            List<AuthorDTO> authorDTOs = book.bookAuthors.collect { new AuthorDTO(it.author) }
            new BookWithDetailsDTO(book, authorDTOs)
        }
    }

    List<BookDTO> getBooksByAuthor(Long authorId) {
        List<Book> books = Book.createCriteria().list {
            bookAuthors {
                author {
                    eq('id', authorId)
                }
            }
        }
        books.collect { new BookDTO(it) }
    }
}
