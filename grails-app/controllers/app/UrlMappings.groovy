package app

class UrlMappings {
    static mappings = {
        // Routes for app.AuthorController
        "/authors"(controller: 'author', action: 'getAuthorsWithDetails', method: 'GET')
        "/authors/list"(controller: 'author', action: 'getAuthorsList', method: 'GET')
        "/authors"(controller: 'author', action: 'createAuthor', method: 'POST')
        "/authors/$id"(controller: 'author', action: 'updateAuthor', method: 'PUT')
        "/authors/$id"(controller: 'author', action: 'deleteAuthor', method: 'DELETE')

        // Routes for app.BookController
        "/books"(controller: 'book', action: 'listBooks', method: 'GET')
        "/books/author/$authorId"(controller: 'book', action: 'getBooksByAuthor', method: 'GET')
        "/books"(controller: 'book', action: 'createBook', method: 'POST')
        "/books/$id"(controller: 'book', action: 'updateBook', method: 'PUT')
        "/books/$id"(controller: 'book', action: 'deleteBook', method: 'DELETE')

        // Routes for app.GenreController
        "/genres"(controller: 'genre', action: 'getGenresWithDetails', method: 'GET')
        "/genres/list"(controller: 'genre', action: 'getGenresList', method: 'GET')
        "/genres"(controller: 'genre', action: 'createGenre', method: 'POST')
        "/genres/$id"(controller: 'genre', action: 'updateGenre', method: 'PUT')
        "/genres/$id"(controller: 'genre', action: 'deleteGenre', method: 'DELETE')
    }
}
