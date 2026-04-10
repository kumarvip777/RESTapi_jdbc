package org.kumar.restapi_jdbc.service;

import org.kumar.restapi_jdbc.entity.Actor;
import org.kumar.restapi_jdbc.entity.Movie;
import org.kumar.restapi_jdbc.exception.BadRequestException;
import org.kumar.restapi_jdbc.exception.ResourceNotFoundException;
import org.kumar.restapi_jdbc.repository.ActorRepository;
import org.kumar.restapi_jdbc.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository,
                        ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    // 🔥 CREATE Movie with Actors
    public Movie saveMovie(Movie movie) {

        if (movie == null) {
            throw new BadRequestException("Movie request body is missing");
        }

        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            throw new BadRequestException("Movie title is required");
        }

        List<Actor> finalActors = new ArrayList<>();

        if (movie.getActors() != null) {

            for (Actor actor : movie.getActors()) {

                if (actor == null) continue;

                // ✅ Existing actor
                if (actor.getId() != null) {

                    if (actor.getId() <= 0) {
                        throw new BadRequestException("Invalid actor ID: " + actor.getId());
                    }

                    Actor existingActor = actorRepository.findById(actor.getId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Actor not found with id: " + actor.getId()));

                    finalActors.add(existingActor);

                } else {

                    // ✅ New actor validation
                    if (actor.getName() == null || actor.getName().isBlank()) {
                        throw new BadRequestException("Actor name is required");
                    }

                    finalActors.add(actor);
                }
            }
        }

        movie.setActors(finalActors);

        Movie savedMovie = movieRepository.save(movie);

        // 🔥 Sync both sides
        for (Actor actor : finalActors) {
            if (actor.getMovies() == null) {
                actor.setMovies(new ArrayList<>());
            }
            actor.getMovies().add(savedMovie);
        }

        return savedMovie;
    }

    // 🔥 GET ALL
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // 🔥 GET BY ID
    public Movie getMovieById(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid movie ID");
        }

        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found with id: " + id));
    }

    // 🔥 UPDATE
    public Movie updateMovie(Long id, Movie updatedMovie) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid movie ID");
        }

        Movie existing = getMovieById(id);

        if (updatedMovie.getTitle() != null && !updatedMovie.getTitle().isBlank())
            existing.setTitle(updatedMovie.getTitle());

        if (updatedMovie.getGenre() != null)
            existing.setGenre(updatedMovie.getGenre());

        if (updatedMovie.getDuration() > 0)
            existing.setDuration(updatedMovie.getDuration());

        if (updatedMovie.getRating() > 0)
            existing.setRating(updatedMovie.getRating());

        if (updatedMovie.getReleaseDate() != null)
            existing.setReleaseDate(updatedMovie.getReleaseDate());

        if (updatedMovie.getLanguage() != null)
            existing.setLanguage(updatedMovie.getLanguage());

        if (updatedMovie.getBudget() > 0)
            existing.setBudget(updatedMovie.getBudget());

        return movieRepository.save(existing);
    }

    // 🔥 DELETE
    public void deleteMovie(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid movie ID");
        }

        Movie movie = getMovieById(id);

        movieRepository.delete(movie);
    }
}