package app

import app.request.BookRequestDTO

class BookController {

    def bookService

    static responseFormats = ['json', 'xml']

    def listBooks(Integer max, Integer offset, String query) {
        max = max ?: 5
        offset = offset ?: 0
        query = query?.trim()?.toLowerCase() ?: ''

        if (query == '') {
            query = null
        }

        int totalBooks = bookService.getTotalBooksCount(query) as int
        def books = totalBooks > 0 ? bookService.getAllBooks(max, offset, query) : []

        respond([books: books, totalBooks: totalBooks], view: 'listBooks')
    }

    def getBooksByAuthor(Long authorId) {
        def books = bookService.getBooksByAuthor(authorId)
        respond([books: books], view: 'getBooksByAuthor')
    }

    def createBook(BookRequestDTO bookDTO) {
        if (bookDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, bookDTO, this)
            return
        }
        def response = bookService.addBook(bookDTO)
        respond([message: response.message, book: response.book], status: response.status, view: 'bookResponse')
    }

    def updateBook(Long id, BookRequestDTO bookDTO) {
        if (bookDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, bookDTO, this)
            return
        }
        def response = bookService.updateBook(id, bookDTO)
        respond([message: response.message, book: response.book], status: response.status, view: 'bookResponse')
    }

    def deleteBook(Long id) {
        def response = bookService.deleteBook(id)
        respond([message: response.message], status: response.status)
    }
}
