package app.internal

import app.Genre

class GenreDTO {
    Long id
    String name

    GenreDTO(Genre genre) {
        this.id = genre.id
        this.name = genre.name
    }
}
