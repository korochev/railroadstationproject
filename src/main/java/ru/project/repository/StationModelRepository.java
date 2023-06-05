package ru.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.StationModel;

import java.util.Optional;

@Repository
public interface StationModelRepository extends JpaRepository<StationModel, Long> {
    Optional<StationModel> findStationModelByName(String name);
}
