package app

import app.request.GenreRequestDTO

class GenreController {

    def genreService

    static responseFormats = ['json', 'xml']

    def getGenresWithDetails(Integer max, Integer offset) {
        max = max ?: 5
        offset = offset ?: 0
        def totalGenres = genreService.getTotalGenresCount()
        def genres = totalGenres > 0 ? genreService.getAllGenres(max, offset, totalGenres) : []

        respond([genres: genres, totalGenres: totalGenres], view: 'getGenresWithDetails')
    }

    def getGenresList() {
        def genresList = genreService.getGenresList()
        respond([genres: genresList], view: 'getGenresList')
    }

    def createGenre(GenreRequestDTO genreDTO) {
        if (genreDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, genreDTO, this)
            return
        }

        def response = genreService.addGenre(genreDTO)
        respond([message: response.message, genre: response.genre], status: response.status, view: 'genreResponse')
    }

    def updateGenre(Long id, GenreRequestDTO genreDTO) {
        if (genreDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, genreDTO, this)
            return
        }

        def response = genreService.updateGenre(id, genreDTO)
        respond([message: response.message, genre: response.genre], status: response.status, view: 'genreResponse')
    }

    def deleteGenre(Long id) {
        def response = genreService.deleteGenre(id)
        respond([message: response.message], status: response.status)
    }
}
