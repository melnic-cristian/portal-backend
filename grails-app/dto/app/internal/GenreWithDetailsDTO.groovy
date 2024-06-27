package app.internal;

import app.Genre;

class GenreWithDetailsDTO {
    Long id
    String name
    String description

    GenreWithDetailsDTO(Genre genre) {
        this.id = genre.id
        this.name = genre.name
        this.description = genre.description
    }
}
