package ar.edu.uade.api.deliverygo.excepcion;

import ar.edu.uade.api.deliverygo.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponseDto> build(HttpStatus s, Exception ex, HttpServletRequest r) {
        return ResponseEntity.status(s).body(new ErrorResponseDto(LocalDateTime.now(), s.value(), s.name(), ex.getMessage(), r.getRequestURI()));
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDto> notFound(RecursoNoEncontradoException ex, HttpServletRequest r) {
        return build(HttpStatus.NOT_FOUND, ex, r);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorResponseDto> conflict(RecursoDuplicadoException ex, HttpServletRequest r) {
        return build(HttpStatus.CONFLICT, ex, r);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponseDto> badRequest(ReglaNegocioException ex, HttpServletRequest r) {
        return build(HttpStatus.BAD_REQUEST, ex, r);
    }
}
