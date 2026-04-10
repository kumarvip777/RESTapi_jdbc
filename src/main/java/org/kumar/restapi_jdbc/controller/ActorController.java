package org.kumar.restapi_jdbc.controller;

import jakarta.validation.Valid;
import org.kumar.restapi_jdbc.entity.Actor;
import org.kumar.restapi_jdbc.service.ActorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService service;

    public ActorController(ActorService service) {
        this.service = service;
    }

    // 🔥 CREATE Actor
    @PostMapping
    public Actor createActor(@Valid @RequestBody Actor actor) {
        return service.saveActor(actor);
    }

    // 🔥 GET ALL
    @GetMapping
    public List<Actor> getAllActors() {
        return service.getAllActors();
    }

    // 🔥 GET BY ID
    @GetMapping("/{id}")
    public Actor getActorById(@PathVariable Long id) {
        return service.getActorById(id);
    }

    // 🔥 UPDATE
    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable Long id,
                             @Valid @RequestBody Actor actor) {
        return service.updateActor(id, actor);
    }

    // 🔥 DELETE
    @DeleteMapping("/{id}")
    public String deleteActor(@PathVariable Long id) {
        service.deleteActor(id);
        return "Actor deleted successfully";
    }
}