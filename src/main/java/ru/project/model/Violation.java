package ru.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record Violation(String fieldName, String message) {

}
