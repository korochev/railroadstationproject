package ru.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.util.List;
import java.util.Set;

@Schema(description = "Единица подвижного состава")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WagonPark {
    @Id
    @Schema(description = "Номер вагона", example = "07722556")
    @Column(length = 8)
    @Pattern(regexp="^\\d{8}$", message="length must be 8")
    @NotNull
    private String number;

    @Schema(description = "Сущность характеристик вагона")
    @OneToOne(cascade = {CascadeType.ALL})
    @MapsId
    @JoinColumn(name = "number")
    private WagonModel wagonModelNumber;

    @Schema(description = "Сущность номенклатуры груза")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "wagons_cargo-model",
            joinColumns = @JoinColumn(name = "wagons_id"),
            inverseJoinColumns = @JoinColumn(name = "cargo-model_id"))
    private Set<CargoModel> cargoModelId;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "railway_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Railway railwayId;

    @Column(name = "order_column")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer order;

    @Schema(description = "Вес груза", example = "1021.0")
    @Positive
    private Double cargoWeight;

    @Schema(description = "Вес вагона", example = "1121.0")
    @Positive
    private Double weight;

    @Schema(description = "Флаг убытия на сеть РЖД")
    private Boolean exploitation;

}
