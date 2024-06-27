package app

import app.request.GenreRequestDTO

class GenreController {

    GenreService genreService

    def getGenresWithDetails(Integer max, Integer offset) {
        max = max ?: 5
        offset = offset ?: 0
        def totalGenres = genreService.getTotalGenresCount()
        def genres = []
        if(totalGenres  > 0){
            genres = genreService.getAllGenres(max, offset, totalGenres)
        }
        respond([genres: genres, totalGenres: totalGenres], formats: ['json'])
    }

        def getGenresList() {
            def genresList = genreService.getGenresList()
            respond genresList ?: [], [formats: ['json']]
        }


        def createGenre(GenreRequestDTO genreDTO) {
        if (genreDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, genreDTO, this)
            return
        }

        def response = genreService.addGenre(genreDTO)
        respond([message: response.message, genre: response.genre], status: response.status, formats: ['json'])
    }

    def updateGenre(Long id, GenreRequestDTO genreDTO) {
        if (genreDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, genreDTO, this)
            return
        }

        def response = genreService.updateGenre(id, genreDTO)
        respond([message: response.message, genre: response.genre], status: response.status, formats: ['json'])
    }

    def deleteGenre(Long id) {
        def response = genreService.deleteGenre(id)
        respond([message: response.message], status: response.status, formats: ['json'])
    }
}
