package app

import app.internal.AuthorDTO
import app.internal.AuthorWithDetailsDTO
import app.request.AuthorRequestDTO
import app.response.AuthorResponseDTO
import org.springframework.http.HttpStatus
import grails.gorm.transactions.Transactional

class AuthorService {

    @Transactional
    AuthorResponseDTO addAuthor(AuthorRequestDTO authorRequestDTO) {
        def author = new Author(
                firstName: authorRequestDTO.firstName,
                lastName: authorRequestDTO.lastName,
                biography: authorRequestDTO.biography
        )

        return author.save(flush: true) ?
                new AuthorResponseDTO('Author created successfully', HttpStatus.CREATED, author) :
                new AuthorResponseDTO('Error creating author', HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @Transactional
    AuthorResponseDTO deleteAuthor(Long id) {
        def author = Author.get(id)
        if (!author) {
            return new AuthorResponseDTO('Author not found to be deleted', HttpStatus.NOT_FOUND)
        }

        if (author.bookAuthors) {
            return new AuthorResponseDTO('Author cannot be deleted as they have associated books.', HttpStatus.BAD_REQUEST)
        }

        author.delete(flush: true)
        return new AuthorResponseDTO('Author deleted successfully', HttpStatus.NO_CONTENT)
    }

    @Transactional
    AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO authorRequestDTO) {
        def author = Author.get(id)
        if (author) {
            author.firstName = authorRequestDTO.firstName
            author.lastName = authorRequestDTO.lastName
            author.biography = authorRequestDTO.biography

            return author.save(flush: true) ?
                    new AuthorResponseDTO('Author updated successfully', HttpStatus.OK, author) :
                    new AuthorResponseDTO('Error updating author', HttpStatus.INTERNAL_SERVER_ERROR)
        } else {
            return new AuthorResponseDTO('Author not found to be updated', HttpStatus.NOT_FOUND)
        }
    }

    def getTotalAuthorsCount() {
        Author.count()
    }

    List<AuthorWithDetailsDTO> getAuthorsWithDetails(int max = 5, int offset = 0, int totalAuthors) {
        if (offset >= totalAuthors) return []

        max = Math.min(max, totalAuthors - offset)

        List<Author> authors = Author.createCriteria().list {
            maxResults(max)
            firstResult(offset)
            order("lastUpdated", "desc")
        }

        authors.collect { author -> new AuthorWithDetailsDTO(author) }
    }

    List<AuthorDTO> getAuthorsList() {
        Author.list().collect { author -> new AuthorDTO(author) }
    }
}
