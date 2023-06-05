package ru.project.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.CargoModel;
import ru.project.repository.CargoModelDAO;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CargoModelService {
    private final CargoModelDAO cargoModelDAO;

    public List<CargoModel> showCargoModels() {

        return cargoModelDAO.findAll();
    }

    public CargoModel findById(String code) throws EntityNotFoundException {
        log.info("Search entity with code: {}", code);
        return cargoModelDAO.findById(code).orElseThrow(() -> new EntityNotFoundException(code));
    }

    public CargoModel delete(String code) throws EntityNotFoundException {
        CargoModel wm = this.findById(code);
        log.info("Delete entity: {}", wm);
        cargoModelDAO.deleteById(code);
        return wm;
    }

    public CargoModel updateCargoModel(String code, CargoModel cargoModel) throws EntityNotFoundException {
        log.info("Update entity: {}", this.findById(code));
        return this.saveCargoModel(cargoModel);
    }

    public CargoModel saveCargoModel(CargoModel cargoModel) {
        log.info("error will be thrown here");
        return cargoModelDAO.save(cargoModel);
    }
}
