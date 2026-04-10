package org.kumar.restapi_jdbc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @Min(value = 60, message = "Duration must be at least 60 minutes")
    private int duration; // in minutes

    @DecimalMin(value = "1.0", message = "Rating must be at least 1")
    @DecimalMax(value = "10.0", message = "Rating must be at most 10")
    private double rating;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotBlank(message = "Language is required")
    private String language;

    @Positive(message = "Budget must be positive")
    private double budget;


    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )

    private List<Actor> actors;
}