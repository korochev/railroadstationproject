package ru.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.StationModel;
import ru.project.repository.StationModelRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class StationModelService {
    private final StationModelRepository stationModelRepository;

    public StationModel save(final StationModel stationModel) {
        log.info("Save station_model entity: {}", stationModel);
        return stationModelRepository.save(stationModel);
    }

    public List<StationModel> getAll() {
        log.info("Get all station_model entities");
        return stationModelRepository.findAll();
    }

    public StationModel getById(final Long id) throws EntityNotFoundException {
        log.info("Get station_model entity by id: {}", id);
        return stationModelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public StationModel getByName(final String name) throws EntityNotFoundException {
        log.info("Get station_model entity by id: {}", name);
        return stationModelRepository.findStationModelByName(name).orElseThrow(() -> new EntityNotFoundException(name));
    }

    public StationModel update(Long id, final StationModel stationModel) throws EntityNotFoundException {
        log.info("Update station_model entity: {}", this.getById(id));
        return stationModelRepository.save(stationModel);
    }

    public StationModel delete(final Long id) throws EntityNotFoundException {
        StationModel stationModel = this.getById(id);
        log.info("Delete station_model entity: {}", stationModel);
        stationModelRepository.deleteById(id);
        return stationModel;
    }
}
