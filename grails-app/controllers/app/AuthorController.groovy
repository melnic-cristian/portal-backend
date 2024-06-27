package app

import app.request.AuthorRequestDTO

class AuthorController {

    def authorService

    def getAuthorsWithDetails(Integer max, Integer offset) {
        max = max ?: 5
        offset = offset ?: 0

        def totalAuthors = authorService.getTotalAuthorsCount()
        def authors = totalAuthors > 0 ? authorService.getAuthorsWithDetails(max, offset, totalAuthors) : []

        respond([authors: authors, totalAuthors: totalAuthors], formats: ['json'])
    }

    def getAuthorsList() {
        def authorsList = authorService.getAuthorsList()
        respond authorsList ?: [], formats: ['json']
    }

    def createAuthor(AuthorRequestDTO authorDTO) {
        if (authorDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, authorDTO, this)
            return
        }

        def result = authorService.addAuthor(authorDTO)
        respond([message: result.message, author: result.author], status: result.status, formats: ['json'])
    }

    def updateAuthor(Long id, AuthorRequestDTO authorDTO) {
        if (authorDTO.hasErrors()) {
            ErrorHandlingUtils.handleErrors(grailsApplication, authorDTO, this)
            return
        }

        def result = authorService.updateAuthor(id, authorDTO)
        respond([message: result.message, author: result.author], status: result.status, formats: ['json'])
    }

    def deleteAuthor(Long id) {
        def result = authorService.deleteAuthor(id)
        respond([message: result.message], status: result.status, formats: ['json'])
    }
}

