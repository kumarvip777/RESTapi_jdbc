package org.kumar.restapi_jdbc.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kumar.restapi_jdbc.entity.Actor;
import org.kumar.restapi_jdbc.entity.Movie;
import org.kumar.restapi_jdbc.exception.ResourceNotFoundException;
import org.kumar.restapi_jdbc.repository.ActorRepository;
import org.kumar.restapi_jdbc.repository.MovieRepository;
import org.kumar.restapi_jdbc.service.MovieService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void testSaveMovie() {

        Actor actor = new Actor();
        actor.setId(1L);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setActors(List.of(actor));

        Mockito.when(actorRepository.findById(1L))
                .thenReturn(Optional.of(actor));

        Mockito.when(movieRepository.save(movie))
                .thenReturn(movie);

        Movie saved = movieService.saveMovie(movie);

        Assertions.assertEquals("Test Movie", saved.getTitle());
        Assertions.assertEquals(1, saved.getActors().size());

        Mockito.verify(movieRepository).save(movie);
    }

    @Test
    void testSaveMovie_MultipleActors() {


        Actor actor1 = new Actor();
        actor1.setId(1L);

        Actor actor2 = new Actor();
        actor2.setId(2L);


        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setActors(List.of(actor1, actor2));


        Mockito.when(actorRepository.findById(1L))
                .thenReturn(Optional.of(actor1));

        Mockito.when(actorRepository.findById(2L))
                .thenReturn(Optional.of(actor2));


        Mockito.when(movieRepository.save(movie))
                .thenReturn(movie);

        Movie saved = movieService.saveMovie(movie);

        Assertions.assertEquals(2, saved.getActors().size());


        Mockito.verify(actorRepository).findById(1L);
        Mockito.verify(actorRepository).findById(2L);

        Mockito.verify(movieRepository).save(movie);
    }

    @Test
    void testSaveMovie_ActorNotFound() {

        Actor actor = new Actor();
        actor.setId(1L);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setActors(List.of(actor));

        // actor not found
        Mockito.when(actorRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> movieService.saveMovie(movie)
        );

        Mockito.verify(movieRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteMovie() {

        Movie movie = new Movie();
        movie.setId(1L);

        Mockito.when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        movieService.deleteMovie(1L);

        Mockito.verify(movieRepository).delete(movie);
    }
}