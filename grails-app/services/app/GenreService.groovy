package app

import app.request.GenreRequestDTO
import grails.gorm.transactions.Transactional
import org.springframework.http.HttpStatus

class GenreService {

    @Transactional
    Map addGenre(GenreRequestDTO genreRequestDTO) {
        def genre = new Genre(
                name: genreRequestDTO.name,
                description: genreRequestDTO.description
        )
        if (genre.save(flush: true)) {
            return [message: 'Genre created successfully', status: HttpStatus.CREATED, genre: genre]
        } else {
            return [message: 'Error creating genre', status: HttpStatus.INTERNAL_SERVER_ERROR]
        }
    }

    @Transactional
    Map updateGenre(Long id, GenreRequestDTO genreRequestDTO) {
        def genre = Genre.get(id)
        if (!genre) {
            return [message: 'Genre not found to be updated', status: HttpStatus.NOT_FOUND]
        }

        genre.name = genreRequestDTO.name
        genre.description = genreRequestDTO.description
        if (genre.save(flush: true)) {
            return [message: 'Genre updated successfully', status: HttpStatus.OK, genre: genre]
        } else {
            return [message: 'Error updating genre', status: HttpStatus.INTERNAL_SERVER_ERROR]
        }
    }

    @Transactional
    Map deleteGenre(Long id) {
        def genre = Genre.get(id)
        if (!genre) {
            return [message: 'Genre not found to be deleted', status: HttpStatus.NOT_FOUND]
        }

        if (genre.books) {
            return [message: 'Genre cannot be deleted as it has associated books.', status: HttpStatus.BAD_REQUEST]
        }

        genre.delete(flush: true)
        return [message: 'Genre deleted successfully', status: HttpStatus.NO_CONTENT]
    }

    def getTotalGenresCount() {
        Genre.count()
    }

    List<Genre> getAllGenres(int max = 5, int offset = 0, int totalGenres) {
        if (offset >= totalGenres) return []

        max = Math.min(max, totalGenres - offset)

        return Genre.createCriteria().list {
            maxResults(max)
            firstResult(offset)
            order("lastUpdated", "desc")
        }
    }

    List<Genre> getGenresList() {
        Genre.list()
    }
}
