package ru.project.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.controller.WagonModelController;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.WagonModel;
import ru.project.service.WagonModelService;

import java.util.List;

@RestController
@RequestMapping("/wagon-model")
@AllArgsConstructor
public class WagonModelControllerImpl implements WagonModelController {

    private WagonModelService wmService;

    @Override
    @GetMapping
    public ResponseEntity<List<WagonModel>> listWagonModel() {

        return ResponseEntity.ok().body(wmService.showWagonModels());
    }

    @Override
    @GetMapping(value = "/{wmId}")
    public ResponseEntity<WagonModel> getWagonModel(@PathVariable("wmId") String num) throws EntityNotFoundException {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(wmService.findById(num));
    }

    @Override
    @PostMapping
    public ResponseEntity<WagonModel> createWagonModel(@RequestBody WagonModel wagonModel) {

        return ResponseEntity.status(201).body(wmService.saveWagonModel(wagonModel));
    }

    @Override
    @PutMapping(value = "/{wmId}")
    public ResponseEntity<WagonModel> updateWagonModel(@RequestBody WagonModel wagonModel,
                                                       @PathVariable("wmId") String num
    ) throws EntityNotFoundException {

        return ResponseEntity.ok().body(wmService.updateWagonModel(num, wagonModel));
    }

    @Override
    @DeleteMapping(value = "/{wmId}")
    public ResponseEntity<WagonModel> deleteWagonModel(@PathVariable("wmId") String num) throws EntityNotFoundException {

        System.out.println("Current id: " + num);
        return ResponseEntity.ok().body(wmService.delete(num));
    }
}
