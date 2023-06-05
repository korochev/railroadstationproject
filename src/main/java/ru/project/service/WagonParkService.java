package ru.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.WagonPark;
import ru.project.repository.WagonParkRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class WagonParkService {
    private final WagonParkRepository wagonParkRepository;

    public WagonPark save(final WagonPark wagonPark) {
        log.info("Save wagon_park entity: {}", wagonPark);
        return wagonParkRepository.save(wagonPark);
    }

    public List<WagonPark> getAll() {
        log.info("Get all wagon_park entities");
        return wagonParkRepository.findAll();
    }

    public WagonPark getById(final String num) throws EntityNotFoundException {
        log.info("Get wagon_park entity by id: {}", num);
        return wagonParkRepository.findById(num).orElseThrow(() -> new EntityNotFoundException(num));
    }

    public WagonPark update(final String num, final WagonPark wagonPark) throws EntityNotFoundException {
        log.info("Update wagon_park entity: {}", this.getById(num));
        return wagonParkRepository.save(wagonPark);
    }

    public WagonPark delete(final String num) throws EntityNotFoundException {
        WagonPark wagonPark = getById(num);
        log.info("Delete wagon_park entity: {}", wagonPark);
        wagonParkRepository.deleteById(num);
        return wagonPark;
    }
}
