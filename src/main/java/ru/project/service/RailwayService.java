package ru.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.Railway;
import ru.project.repository.RailwayRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class RailwayService {
    private final RailwayRepository railwayRepository;

    public Railway save(final Railway railway) {
        log.info("Save railway entity: {}", railway);
        return railwayRepository.save(railway);
    }

    public List<Railway> getAll() {
        log.info("Get all railway entities");
        return railwayRepository.findAll();
    }

    public Railway getById(final Long id) throws EntityNotFoundException {
        log.info("Get railway entity by id: {}", id);
        return railwayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Railway update(Long id, final Railway railway) throws EntityNotFoundException {
        log.info("Update railway entity: {}", this.getById(id));
        return railwayRepository.save(railway);
    }

    public Railway delete(final Long id) throws EntityNotFoundException {
        Railway railway = this.getById(id);
        log.info("Delete railway entity: {}", railway);
        railwayRepository.deleteById(id);
        return railway;
    }
}
