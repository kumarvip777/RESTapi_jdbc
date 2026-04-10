package org.kumar.restapi_jdbc.controller;

import jakarta.validation.Valid;
import org.kumar.restapi_jdbc.entity.Movie;
import org.kumar.restapi_jdbc.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    // 🔥 1. CREATE Movie with Actors
    @PostMapping
    public Movie createMovie(@Valid @RequestBody Movie movie) {
        return service.saveMovie(movie);
    }

    // 🔥 2. GET ALL Movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    // 🔥 3. GET Movie by ID
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return service.getMovieById(id);
    }

    // 🔥 4. UPDATE Movie
    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id,
                             @Valid @RequestBody Movie movie) {
        return service.updateMovie(id, movie);
    }

    // 🔥 5. DELETE Movie
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        service.deleteMovie(id);
        return "Movie deleted successfully";
    }
}