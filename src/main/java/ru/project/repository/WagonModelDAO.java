package ru.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.WagonModel;

@Repository
public interface WagonModelDAO extends JpaRepository<WagonModel, String> {
}
