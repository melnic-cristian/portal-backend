package app

import app.internal.GenreDTO
import app.internal.GenreWithDetailsDTO
import app.request.GenreRequestDTO
import app.response.GenreResponseDTO
import grails.gorm.transactions.Transactional
import org.springframework.http.HttpStatus

class GenreService {

    @Transactional
    GenreResponseDTO addGenre(GenreRequestDTO genreRequestDTO) {
        def genre = new Genre(
                name: genreRequestDTO.name,
                description: genreRequestDTO.description
        )
        if (genre.save(flush: true)) {
            return new GenreResponseDTO('Genre created successfully', HttpStatus.CREATED, genre)
        } else {
            return new GenreResponseDTO('Error creating genre', HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    GenreResponseDTO updateGenre(Long id, GenreRequestDTO genreRequestDTO) {
        def genre = Genre.get(id)
        if (!genre) {
            return new GenreResponseDTO('Genre not found to be updated', HttpStatus.NOT_FOUND)
        }

        genre.name = genreRequestDTO.name
        genre.description = genreRequestDTO.description
        if (genre.save(flush: true)) {
            return new GenreResponseDTO('Genre updated successfully', HttpStatus.OK, genre)
        } else {
            return new GenreResponseDTO('Error updating genre', HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    GenreResponseDTO deleteGenre(Long id) {
        def genre = Genre.get(id)
        if (!genre) {
            return new GenreResponseDTO('Genre not found to be deleted', HttpStatus.NOT_FOUND)
        }

        genre.delete(flush: true)
        return new GenreResponseDTO('Genre deleted successfully', HttpStatus.NO_CONTENT)
    }

    def getTotalGenresCount() {
        Genre.count()
    }

    List<GenreWithDetailsDTO> getAllGenres(int max = 5, int offset = 0, int totalGenres) {
        if (offset >= totalGenres) return []

        max = Math.min(max, totalGenres - offset)

        List<Genre> genres = Genre.createCriteria().list {
            maxResults(max)
            firstResult(offset)
            order("lastUpdated", "desc")
        }

        genres.collect { new GenreWithDetailsDTO(it) }
    }

    List<GenreDTO> getGenresList() {
        Genre.list().collect { new GenreDTO(it) }
    }
}
