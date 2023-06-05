package ru.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestParametersException extends Exception {

    public InvalidRequestParametersException() {
        super("The parameter does not exist. Must be 'stationName' / 'railwayNom'.");

    }

}
