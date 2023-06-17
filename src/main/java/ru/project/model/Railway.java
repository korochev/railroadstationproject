package ru.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Schema(description = "Сущность жд путей")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Railway {
    @Schema(description = "Идентификатор", example = "11")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Номер пути", example = "1")
    @Min(0)
    @Max(20)
    @NotNull
    private Integer nom;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_model_id")
    @JsonBackReference
    @ToString.Exclude
    private StationModel stationModels;

    @Schema(description = "Список связных сущностей подвижного состава")
    @OneToMany(
            mappedBy = "railwayId",
            cascade = CascadeType.ALL
    )
    @OrderColumn(name = "order_column")
    @JsonManagedReference
    private List<WagonPark> wagons;

    public void addWagonsToBegin(List<WagonPark> wagons) {
        this.wagonsInit(wagons);
        wagons.addAll(this.wagons);
        this.wagons = wagons;
    }

    public void addWagonsToEnd(List<WagonPark> wagons) {
        this.wagonsInit(wagons);
        this.wagons.addAll(wagons);
    }

    private void wagonsInit(List<WagonPark> wagons) {
        if (this.wagons == null) {
            this.wagons = new ArrayList<>();
        }
        wagons.forEach(wagon -> wagon.setRailwayId(this));
    }

    public void removeAllWagon() {
        for (Iterator<WagonPark> iterator = this.wagons.iterator(); iterator.hasNext(); ) {
            WagonPark wagon = iterator.next();
            iterator.remove();
            wagon.setRailwayId(null);
            wagon.setOrder(null);
            wagon.setExploitation(true);
        }
    }
}
