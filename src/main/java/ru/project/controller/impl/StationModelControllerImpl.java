package ru.project.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.controller.StationModelController;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.StationModel;
import ru.project.service.StationModelService;

import java.util.List;

@RestController
@RequestMapping("/station-model")
@AllArgsConstructor
public class StationModelControllerImpl implements StationModelController {

    private final StationModelService stationModelService;

    @Override
    @GetMapping
    public ResponseEntity<List<StationModel>> getAllStationModels() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(stationModelService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<StationModel> getStationModel(@PathVariable("id") Long id) throws EntityNotFoundException {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(stationModelService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<StationModel> createStationModel(@RequestBody StationModel stationModel) {

        return new ResponseEntity<>(stationModelService.save(stationModel), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{Id}")
    public ResponseEntity<StationModel> updateStationModel(@RequestBody StationModel stationModel,
                                                           @PathVariable("Id") Long Id
    ) throws EntityNotFoundException {

        return ResponseEntity.ok(stationModelService.update(Id, stationModel));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<StationModel> deleteStationModel(@PathVariable("id") Long id) throws EntityNotFoundException {

        return ResponseEntity.ok(stationModelService.delete(id));
    }
}
