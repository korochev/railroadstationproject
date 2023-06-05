package ru.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.project.exception.EntityNotFoundException;
import ru.project.exception.InvalidRequestParametersException;
import ru.project.model.Railway;
import ru.project.model.WagonPark;

import java.util.List;
import java.util.Map;

@Tag(name = "Учет маневровых работ", description = "Базовые операции перемещения вагонов мужду и внутри  станций")
@Validated
public interface LogisticController {

    @Operation(
            summary = "Прием вагонов на предприятие",
            description = "Возвращает сущность пути станции"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Прием вагонов на предприятие успешно завершен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Railway.class)
                    )
            )
    )
    ResponseEntity<Railway> addWagonListToTheStationRailway(
            @RequestBody
            @Validated
            @Parameter(description = "Сущность")
            List<WagonPark> wagonList,
            @RequestParam
            @Parameter(description = "Название станции / Номер пути")
            Map<String, String> parameters
    ) throws EntityNotFoundException, InvalidRequestParametersException;

    @Operation(
            summary = "Перестановка вагонов внутри станции",
            description = "Возвращает сущность пути станции"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Перестановка вагонов внутри станции успешно завершена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Railway.class)
                    )
            )
    )
    public ResponseEntity<Railway> changeStationRailway(
            @RequestBody
            @Validated
            @Parameter(description = "Сущность")
            List<WagonPark> wagonList,
            @RequestParam
            @Parameter(description = "Название станции / Номер пути / Направление состава")
            Map<String, String> parameters
    ) throws EntityNotFoundException, InvalidRequestParametersException;

    @Operation(
            summary = "Убытие вагонов на сеть РЖД",
            description = "Возвращает сущность пути станции"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Убытие вагонов на сеть РЖД успешно завершено",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Railway.class)
                    )
            )
    )
    ResponseEntity<Railway> clearStationRailway(
            @RequestParam
            @Parameter(description = "Название станции / Номер пути")
            Map<String, String> parameters
    ) throws EntityNotFoundException, InvalidRequestParametersException;
}
