package ru.project.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.controller.CargoModelController;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.CargoModel;
import ru.project.service.CargoModelService;

import java.util.List;

@RestController
@RequestMapping("/cargo-model")
@AllArgsConstructor
public class CargoModelControllerImpl implements CargoModelController {

    private CargoModelService cmService;

    @Override
    @GetMapping
    public ResponseEntity<List<CargoModel>> listWagonModel() {

        return ResponseEntity.ok().body(cmService.showCargoModels());
    }

    @Override
    @GetMapping(value = "/{cmId}")
    public ResponseEntity<CargoModel> getWagonModel(@PathVariable("cmId") String code) throws EntityNotFoundException {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(cmService.findById(code));
    }

    @Override
    @PostMapping
    public ResponseEntity<CargoModel> createWagonModel(@RequestBody CargoModel cargoModel) {

        return ResponseEntity.status(201).body(cmService.saveCargoModel(cargoModel));
    }

    @Override
    @PutMapping(value = "/{сmId}")
    public ResponseEntity<CargoModel> updateWagonModel(@RequestBody CargoModel cargoModel,
                                                       @PathVariable("сmId") String code) throws EntityNotFoundException {

        return ResponseEntity.ok().body(cmService.updateCargoModel(code, cargoModel));
    }

    @Override
    @DeleteMapping(value = "/{cmId}")
    public ResponseEntity<CargoModel> deleteWagonModel(@PathVariable("cmId") String code) throws EntityNotFoundException {

        return ResponseEntity.ok().body(cmService.delete(code));
    }
}
