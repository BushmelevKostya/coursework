package itmo.coursework.api.util;

import itmo.coursework.dto.ExceptionResponseDTO;
import itmo.coursework.exceptions.UnauthorizedUserException;
import itmo.coursework.exceptions.entity.base.EntityExistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            EntityExistenceException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleEntityAbsenceException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDTO(e.getMessage()));
    }


    @ExceptionHandler({
            UnauthorizedUserException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleUnauthorizedException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponseDTO(e.getMessage()));
    }
}
