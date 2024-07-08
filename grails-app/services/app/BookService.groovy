package app

import app.request.BookRequestDTO
import grails.gorm.transactions.Transactional
import org.springframework.http.HttpStatus

class BookService {

    @Transactional
    Map addBook(BookRequestDTO bookRequestDTO) {
        def book = new Book(
                title: bookRequestDTO.title,
                isbn: bookRequestDTO.isbn,
                summary: bookRequestDTO.summary,
                publicDate: bookRequestDTO.publicDate,
                imageLink: bookRequestDTO.imageLink
        )

        if (bookRequestDTO.genreId) {
            Genre genre = Genre.get(bookRequestDTO.genreId)
            if (!genre) {
                return [message: 'Genre not found', status: HttpStatus.NOT_FOUND]
            } else {
                book.genre = genre
            }
        }

        if (book.save(flush: true)) {
            bookRequestDTO.authorIds.each { authorId ->
                Author author = Author.get(authorId)
                if (author) {
                    new BookAuthor(book: book, author: author).save(flush: true)
                }
            }
            book.refresh()
            return [message: 'Book created successfully', status: HttpStatus.CREATED, book: book]
        } else {
            return [message: 'Error creating book', status: HttpStatus.INTERNAL_SERVER_ERROR]
        }
    }

    @Transactional
    Map updateBook(Long bookId, BookRequestDTO bookRequestDTO) {
        Book book = Book.get(bookId)
        if (!book) {
            return [message: 'Book not found', status: HttpStatus.NOT_FOUND]
        }

        if (bookRequestDTO.genreId) {
            Genre genre = Genre.get(bookRequestDTO.genreId)
            if (!genre) {
                return [message: 'Genre not found', status: HttpStatus.NOT_FOUND]
            }
            book.genre = genre
        } else {
            book.genre = null
        }

        book.title = bookRequestDTO.title
        book.isbn = bookRequestDTO.isbn
        book.summary = bookRequestDTO.summary
        book.publicDate = bookRequestDTO.publicDate
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
            return [message: 'Book updated successfully', status: HttpStatus.OK, book: book]
        } else {
            return [message: 'Error updating book', status: HttpStatus.INTERNAL_SERVER_ERROR]
        }
    }

    @Transactional
    Map deleteBook(Long id) {
        def book = Book.get(id)
        if (book) {
            book.delete(flush: true)
            return [message: 'Book deleted successfully', status: HttpStatus.NO_CONTENT]
        } else {
            return [message: 'Book not found to be deleted', status: HttpStatus.NOT_FOUND]
        }
    }

    int getTotalBooksCount(String query = null) {
        if (query) {
            return Book.createCriteria().get {
                projections {
                    count()
                }
                ilike("title", "%${query}%")
            } as int
        } else {
            return Book.count() as int
        }
    }

    List<Book> getAllBooks(int max = 5, int offset = 0, String query = '') {
        query = query?.trim()?.toLowerCase() ?: ''

        def totalBooks = Book.createCriteria().count {
            if (query) {
                or {
                    ilike('title', "%${query}%")
                }
            }
        }

        if (offset >= totalBooks) return []

        max = Math.min(max, totalBooks - offset)

        return Book.createCriteria().list {
            if (query) {
                or {
                    ilike('title', "%${query}%")
                }
            }
            maxResults(max)
            firstResult(offset)
            order("lastUpdated", "desc")
        }
    }

    List<Book> getBooksByAuthor(Long authorId) {
        return Book.createCriteria().list {
            bookAuthors {
                author {
                    eq('id', authorId)
                }
            }
        }
    }
}
