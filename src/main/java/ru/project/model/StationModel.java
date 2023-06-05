package ru.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Сущность станционной модели")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "station_model")
public class StationModel {
    @Schema(description = "Идентификатор", example = "11")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Наименование", example = "Расторгуево")
    @NotNull
    private String name;
    
    @Schema(description = "Список связных сущностей станционных путей")
    @OneToMany(
            mappedBy = "stationModels",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Railway> railways;

    public void addRailway(Railway rail) {
        if (railways == null) {
            this.railways = new ArrayList<>();
        }
        railways.add(rail);
        rail.setStationModels(this);
    }

    public void removeRailway(Railway rail) {
        if (railways != null) {
            this.railways.remove(rail);
            rail.setStationModels(null);
        }
    }
}
