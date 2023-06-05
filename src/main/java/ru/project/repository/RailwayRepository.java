package ru.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.Railway;

@Repository
public interface RailwayRepository extends JpaRepository<Railway, Long> {
}
