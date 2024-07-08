package app

class UrlMappings {

    static mappings = {
        "/authors"(controller: 'author', action: 'getAuthorsWithDetails', method: 'GET') {
            format = 'json'
        }
        "/authors/list"(controller: 'author', action: 'getAuthorsList', method: 'GET') {
            format = 'json'
        }
        "/authors"(controller: 'author', action: 'createAuthor', method: 'POST') {
            format = 'json'
        }
        "/authors/$id"(controller: 'author', action: 'updateAuthor', method: 'PUT') {
            format = 'json'
        }
        "/authors/$id"(controller: 'author', action: 'deleteAuthor', method: 'DELETE') {
            format = 'json'
        }

        "/books"(controller: 'book', action: 'listBooks', method: 'GET') {
            format = 'json'
        }
        "/books/author/$authorId"(controller: 'book', action: 'getBooksByAuthor', method: 'GET') {
            format = 'json'
        }
        "/books"(controller: 'book', action: 'createBook', method: 'POST') {
            format = 'json'
        }
        "/books/$id"(controller: 'book', action: 'updateBook', method: 'PUT') {
            format = 'json'
        }
        "/books/$id"(controller: 'book', action: 'deleteBook', method: 'DELETE') {
            format = 'json'
        }

        "/genres"(controller: 'genre', action: 'getGenresWithDetails', method: 'GET') {
            format = 'json'
        }
        "/genres/list"(controller: 'genre', action: 'getGenresList', method: 'GET') {
            format = 'json'
        }
        "/genres"(controller: 'genre', action: 'createGenre', method: 'POST') {
            format = 'json'
        }
        "/genres/$id"(controller: 'genre', action: 'updateGenre', method: 'PUT') {
            format = 'json'
        }
        "/genres/$id"(controller: 'genre', action: 'deleteGenre', method: 'DELETE') {
            format = 'json'
        }
    }
}
