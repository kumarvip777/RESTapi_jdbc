package org.kumar.restapi_jdbc.repository;

import org.kumar.restapi_jdbc.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    boolean existsByName(String name);
}