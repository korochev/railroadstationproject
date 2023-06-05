package ru.project.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.controller.WagonParkController;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.WagonPark;
import ru.project.service.WagonParkService;

import java.util.List;

@RestController
@RequestMapping("/wagon-park")
@AllArgsConstructor
public class WagonParkControllerImpl implements WagonParkController {

    private final WagonParkService wagonParkService;

    @Override
    @GetMapping
    public ResponseEntity<List<WagonPark>> getAllWagonParks() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(wagonParkService.getAll());
    }

    @Override
    @GetMapping("/{wmId}")
    public ResponseEntity<WagonPark> getWagonPark(@PathVariable("wmId") String num) throws EntityNotFoundException {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(wagonParkService.getById(num));
    }

    @Override
    @PostMapping
    public ResponseEntity<WagonPark> createWagonPark(@RequestBody WagonPark wagonPark) {

        return new ResponseEntity<>(wagonParkService.save(wagonPark), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{wmId}")
    public ResponseEntity<WagonPark> updateWagonPark(@RequestBody WagonPark wagonPark,
                                                     @PathVariable("wmId") String num
    ) throws EntityNotFoundException {

        return ResponseEntity.ok(wagonParkService.update(num, wagonPark));
    }

    @Override
    @DeleteMapping("/{wmId}")
    public ResponseEntity<WagonPark> deleteWagonPark(@PathVariable("wmId") String num) throws EntityNotFoundException {

        return ResponseEntity.ok(wagonParkService.delete(num));
    }
}
