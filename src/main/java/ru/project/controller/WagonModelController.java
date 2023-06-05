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
import ru.project.model.StationModel;
import ru.project.model.WagonModel;

import java.util.List;

@Tag(name = "Паспорт вагонов", description = "CRUD-операции основных характеристик вагона")
@Validated
public interface WagonModelController {

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
                            schema = @Schema(implementation = WagonModel.class)
                    )
            )
    )
    ResponseEntity<List<WagonModel>> listWagonModel();

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
                            schema = @Schema(implementation = WagonModel.class)
                    )
            )
    )
    ResponseEntity<WagonModel> getWagonModel(
            @PathVariable
            @Pattern(regexp = "^\\d{8}$", message = "length must be 8")
            @Parameter(description = "Номер вагона")
            String num
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
                            schema = @Schema(implementation = WagonModel.class)
                    )
            )
    )
    ResponseEntity<WagonModel> createWagonModel(
            @RequestBody
            @Validated
            @Parameter(description = "Сущность")
            WagonModel wagonModel
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
                            schema = @Schema(implementation = WagonModel.class)
                    )
            )
    )
    ResponseEntity<WagonModel> updateWagonModel(
            @RequestBody
            @Validated
            @Parameter(description = "Сущность")
            WagonModel wagonModel,
            @PathVariable
            @Pattern(regexp = "^\\d{8}$", message = "length must be 8")
            @Parameter(description = "Номер вагона")
            String num
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Удалить запись",
            description = "Возвращает объект с указанным pk после удаления"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление конкретной записи успешно завершен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WagonModel.class)
                    )
            )
    )
    ResponseEntity<WagonModel> deleteWagonModel(
            @PathVariable
            @Pattern(regexp = "^\\d{8}$", message = "length must be 8")
            @Parameter(description = "Номер вагона")
            String num
    ) throws EntityNotFoundException;
}
