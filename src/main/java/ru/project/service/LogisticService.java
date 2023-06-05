package ru.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.exception.EntityNotFoundException;
import ru.project.exception.InvalidRequestParametersException;
import ru.project.model.Railway;
import ru.project.model.StationModel;
import ru.project.model.WagonPark;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class LogisticService {
    private final StationModelService smService;

    public Railway increaseRailwayTrain(List<WagonPark> wagonList, Map<String, String> parameters) throws EntityNotFoundException, InvalidRequestParametersException {
        log.info("Received wagons: {}", wagonList);
        Consumer<Railway> consumer = rail -> rail.addWagonsToEnd(wagonList);
        return this.findRailwayByNomAdnStationName(consumer, parameters);
    }

    public Railway departureWagonsToTheTrack(Map<String, String> parameters) throws EntityNotFoundException, InvalidRequestParametersException {
        Consumer<Railway> consumer = rail -> rail.removeAllWagon();
        return this.findRailwayByNomAdnStationName(consumer, parameters);
    }
    public Railway moveWagons(List<WagonPark> wagonList, Map<String, String> parameters) throws EntityNotFoundException, InvalidRequestParametersException {
        log.info("Received wagons: {}", wagonList);
        if (parameters.containsKey("dir")) {
            Consumer<Railway> consumer = (parameters.get("dir").equals("end")) ? rail -> rail.addWagonsToEnd(wagonList) : rail -> rail.addWagonsToBegin(wagonList);
            return this.findRailwayByNomAdnStationName(consumer, parameters);
        }
        throw new InvalidRequestParametersException();
    }

    public Railway findRailwayByNomAdnStationName (Consumer<Railway> consumer, Map<String, String> parameters) throws InvalidRequestParametersException, EntityNotFoundException {
        log.info("Request parameters: {}", parameters);
        if (!parameters.containsKey("stationName") && !parameters.containsKey("railwayNom")) {
            throw new InvalidRequestParametersException();
        }
        StationModel sm = smService.getByName(parameters.get("stationName"));
        log.info("Get station_model entity: {}", sm);

        Integer railwayNom = Integer.parseInt(parameters.get("railwayNom"));
        for (Railway rail : sm.getRailways()) {
            if (Objects.equals(rail.getNom(), railwayNom)) {
                consumer.accept(rail);
                smService.save(sm);
                return rail;
            }
        }

        throw new EntityNotFoundException(railwayNom);
    }
}
