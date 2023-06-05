package ru.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.exception.EntityNotFoundException;
import ru.project.model.WagonModel;
import ru.project.repository.WagonModelDAO;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class WagonModelService {
    private final WagonModelDAO wagonModelDAO;

    public List<WagonModel> showWagonModels() {

        return wagonModelDAO.findAll();
    }

    public WagonModel findById(String num) throws EntityNotFoundException {
        log.info("Search entity with id: {}", num);
        return wagonModelDAO.findById(num).orElseThrow(() -> new EntityNotFoundException(num));
    }

    public WagonModel delete(String num) throws EntityNotFoundException {
        WagonModel wm = this.findById(num);
        log.info("Delete entity: {}", wm);
        wagonModelDAO.deleteById(num);
        return wm;
    }

    public WagonModel updateWagonModel(String num, WagonModel wagonModel) throws EntityNotFoundException {
        log.info("Update entity: {}", this.findById(num));
        return this.saveWagonModel(wagonModel);
    }

    public WagonModel saveWagonModel(WagonModel wagonModel) {
        return wagonModelDAO.save(wagonModel);
    }
}
