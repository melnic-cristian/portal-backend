package app

import app.request.AuthorRequestDTO
import org.springframework.http.HttpStatus
import grails.gorm.transactions.Transactional

class AuthorService {

    @Transactional
    Map addAuthor(AuthorRequestDTO authorRequestDTO) {
        def author = new Author(
                firstName: authorRequestDTO.firstName,
                lastName: authorRequestDTO.lastName,
                biography: authorRequestDTO.biography
        )

        if (author.save(flush: true)) {
            return [message: 'Author created successfully', status: HttpStatus.CREATED, author: author]
        } else {
            return [message: 'Error creating author', status: HttpStatus.INTERNAL_SERVER_ERROR]
        }
    }

    @Transactional
    Map deleteAuthor(Long id) {
        def author = Author.get(id)
        if (!author) {
            return [message: 'Author not found to be deleted', status: HttpStatus.NOT_FOUND]
        }

        if (author.bookAuthors) {
            return [message: 'Author cannot be deleted as they have associated books.', status: HttpStatus.BAD_REQUEST]
        }

        author.delete(flush: true)
        return [message: 'Author deleted successfully', status: HttpStatus.NO_CONTENT]
    }

    @Transactional
    Map updateAuthor(Long id, AuthorRequestDTO authorRequestDTO) {
        def author = Author.get(id)
        if (author) {
            author.firstName = authorRequestDTO.firstName
            author.lastName = authorRequestDTO.lastName
            author.biography = authorRequestDTO.biography

            if (author.save(flush: true)) {
                return [message: 'Author updated successfully', status: HttpStatus.OK, author: author]
            } else {
                return [message: 'Error updating author', status: HttpStatus.INTERNAL_SERVER_ERROR]
            }
        } else {
            return [message: 'Author not found to be updated', status: HttpStatus.NOT_FOUND]
        }
    }

    def getTotalAuthorsCount() {
        Author.count()
    }

    List<Author> getAuthorsWithDetails(int max = 5, int offset = 0, int totalAuthors) {
        if (offset >= totalAuthors) return []

        max = Math.min(max, totalAuthors - offset)

        List<Author> authors = Author.createCriteria().list {
            maxResults(max)
            firstResult(offset)
            order("lastUpdated", "desc")
        }

        return authors
    }

    List<Author> getAuthorsList() {
        Author.list()
    }
}
