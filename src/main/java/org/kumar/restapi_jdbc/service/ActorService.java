package org.kumar.restapi_jdbc.service;

import org.kumar.restapi_jdbc.entity.Actor;
import org.kumar.restapi_jdbc.exception.BadRequestException;
import org.kumar.restapi_jdbc.exception.DuplicateResourceException;
import org.kumar.restapi_jdbc.exception.ResourceNotFoundException;
import org.kumar.restapi_jdbc.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepository repository;

    public ActorService(ActorRepository repository) {
        this.repository = repository;
    }


    public Actor saveActor(Actor actor) {

        if (actor.getName() == null || actor.getName().isBlank()) {
            throw new BadRequestException("Actor name must not be empty");
        }

        if (repository.existsByName(actor.getName())) {
            throw new DuplicateResourceException(
                    "Actor already exists with name: " + actor.getName()
            );
        }

        return repository.save(actor);
    }

    public List<Actor> getAllActors() {
        return repository.findAll();
    }

    public Actor getActorById(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid actor ID");
        }

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Actor not found with id: " + id));
    }

    public Actor updateActor(Long id, Actor updatedActor) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid actor ID");
        }

        Actor existing = getActorById(id);

        if (updatedActor.getName() != null && !updatedActor.getName().isBlank()) {

            String newName = updatedActor.getName().trim();

            if (!newName.equals(existing.getName()) &&
                    repository.existsByName(newName)) {

                throw new DuplicateResourceException(
                        "Actor already exists with name: " + newName
                );
            }

            existing.setName(newName);
        }
        if (updatedActor.getAge() > 0) {

            if (updatedActor.getAge() < 18) {
                throw new BadRequestException("Actor must be at least 18 years old");
            }

            existing.setAge(updatedActor.getAge());
        }


        if (updatedActor.getNationality() != null &&
                !updatedActor.getNationality().isBlank()) {

            existing.setNationality(updatedActor.getNationality().trim());
        }

        if (updatedActor.getExperienceYears() > 0) {

            existing.setExperienceYears(updatedActor.getExperienceYears());
        }

        return repository.save(existing);
    }

    public void deleteActor(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid actor ID");
        }

        Actor actor = getActorById(id);

        repository.delete(actor);
    }
}