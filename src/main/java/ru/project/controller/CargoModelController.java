package ru.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.CargoModel;

import java.util.List;

@Tag(name = "Номенклатуры грузов", description = "CRUD-операции справочника ЕТСНГ")
@Validated
public interface CargoModelController {

    @Operation(
            summary = "Поиск всех записей",
            description = "Возвращает список существующих объектов"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск всех записей успешно завершен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CargoModel.class)
                    )
            )
    )
    ResponseEntity<List<CargoModel>> listWagonModel();

    @Operation(
            summary = "Поиск конкретной записи",
            description = "Возвращает строку из базы по pk"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск конкретной записи успешно завершен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CargoModel.class)
                    )
            )
    )
    ResponseEntity<CargoModel> getWagonModel(
            @PathVariable
            @Pattern(regexp = "^\\d{6}$", message = "length must be 6")
            @Parameter(description = "Код")
            String code
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Добавить запись",
            description = "Возвращает созданную сущность"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление записи успешно завершено",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CargoModel.class)
                    )
            )
    )
    ResponseEntity<CargoModel> createWagonModel(
            @RequestBody
            @Validated
            @Parameter(description = "Сущность")
            CargoModel cargoModel
    );

    @Operation(
            summary = "Изменить запись",
            description = "Возвращает изменный объект"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменение конкретной записи успешно завершено",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CargoModel.class)
                    )
            )
    )
    ResponseEntity<CargoModel> updateWagonModel(
            @RequestBody
            @Validated
            @Parameter(description = "Сущность")
            CargoModel cargoModel,
            @PathVariable
            @Pattern(regexp = "^\\d{6}$", message = "length must be 6")
            @Parameter(description = "Код")
            String code
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Удалить запись",
            description = "Возвращает объект с указанным pk после удаления"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление конкретной записи успешно завершено",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CargoModel.class)
                    )
            )
    )
    ResponseEntity<CargoModel> deleteWagonModel(
            @PathVariable
            @Pattern(regexp = "^\\d{6}$", message = "length must be 6")
            @Parameter(description = "Код")
            String code
    ) throws EntityNotFoundException;
}
