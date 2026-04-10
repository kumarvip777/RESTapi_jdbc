package org.kumar.restapi_jdbc.repository;

import org.kumar.restapi_jdbc.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}