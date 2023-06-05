package ru.project.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.controller.LogisticController;
import ru.project.exception.EntityNotFoundException;
import ru.project.exception.InvalidRequestParametersException;
import ru.project.model.Railway;
import ru.project.model.WagonPark;
import ru.project.service.LogisticService;
import ru.project.service.StationModelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistic")
public class LogisticControllerImpl implements LogisticController {
    private final StationModelService smService;
    private final LogisticService logService;
    private ObjectMapper objectMapper;

    public LogisticControllerImpl(StationModelService smService, LogisticService logService, ObjectMapper objectMapper) {
        this.smService = smService;
        this.logService = logService;
        this.objectMapper = objectMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<Railway> addWagonListToTheStationRailway(@RequestBody List<WagonPark> wagonList,
                                                                   @RequestParam Map<String, String> parameters
    ) throws EntityNotFoundException, InvalidRequestParametersException {

        return new ResponseEntity<>(logService.increaseRailwayTrain(wagonList, parameters), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<Railway> changeStationRailway(@RequestBody List<WagonPark> wagonList,
                                                        @RequestParam Map<String, String> parameters
    ) throws EntityNotFoundException, InvalidRequestParametersException {

        return ResponseEntity.ok(logService.moveWagons(wagonList, parameters));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Railway> clearStationRailway(
            @RequestParam Map<String, String> parameters
    ) throws EntityNotFoundException, InvalidRequestParametersException {

        return ResponseEntity.ok(logService.departureWagonsToTheTrack(parameters));
    }

}
