package ru.project.model;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {

}
