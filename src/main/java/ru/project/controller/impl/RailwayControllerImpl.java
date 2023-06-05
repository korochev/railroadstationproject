package ru.project.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.controller.RailwayController;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.Railway;
import ru.project.service.RailwayService;

import java.util.List;

@RestController
@RequestMapping("/railway")
@AllArgsConstructor
public class RailwayControllerImpl implements RailwayController {

    private final RailwayService railwayService;

    @Override
    @GetMapping
    public ResponseEntity<List<Railway>> getAllStationModels() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(railwayService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Railway> getStationModel(@PathVariable("id") Long id) throws EntityNotFoundException {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(railwayService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Railway> createStationModel(@RequestBody Railway railway) {

        return new ResponseEntity<>(railwayService.save(railway), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{Id}")
    public ResponseEntity<Railway> updateStationModel(@RequestBody Railway railway,
                                                      @PathVariable("Id") Long Id
    ) throws EntityNotFoundException {

        return ResponseEntity.ok(railwayService.update(Id, railway));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Railway> deleteStationModel(@PathVariable("id") Long id) throws EntityNotFoundException {

        return ResponseEntity.ok(railwayService.delete(id));
    }
}
