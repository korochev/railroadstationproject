package ru.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.CargoModel;

@Repository
public interface CargoModelDAO extends JpaRepository<CargoModel, String> {
}
