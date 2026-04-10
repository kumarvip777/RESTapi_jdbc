package org.kumar.restapi_jdbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Actor name is required")
    private String name;

    @Min(value = 18, message = "Actor must be at least 18 years old")
    private int age;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @Positive(message = "Experience must be positive")
    private int experienceYears;


    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private List<Movie> movies;
}