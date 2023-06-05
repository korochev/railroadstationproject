package ru.project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность характеристик вагона")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wagon_model")
public class WagonModel {
    @Schema(description = "Номер вагона", example = "07722556")
    @Id
    @Column(length = 8)
    @Pattern(regexp="^\\d{8}$", message="length must be 8")
    @NotNull
    private String number;
    @Schema(description = "Тип вагона", example = "HOPPER")
    @NotNull
    private wagonType type;
    @Schema(description = "Вес тары", example = "251.0")
    @Column(name = "tare_weight")
    @PositiveOrZero
    private double tareWeight;
    @Schema(description = "Грузоподъемность", example = "1021.0")
    @Positive
    private double tonnage;

    public enum wagonType {
        BOXCAR,
        TANK,
        GONDOLA,
        CONVEYOR,
        DUMPCAR,
        HOPPER,
        CARRIER
    }
}
