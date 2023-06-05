package ru.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Schema(description = "Сущность номенклатуры груза")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo_model")
public class CargoModel {
    @Schema(description = "Код", example = "691005")
    @Id
    @Column(length = 6)
    @Pattern(regexp="^\\d{6}$", message="length must be 6")
    @NotNull
    private String code;
    @Schema(description = "Наименование груза")
    @NotBlank
    private String name;
}
