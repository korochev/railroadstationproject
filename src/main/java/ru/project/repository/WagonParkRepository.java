package ru.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.WagonPark;

@Repository
public interface WagonParkRepository extends JpaRepository<WagonPark, String> {
}
