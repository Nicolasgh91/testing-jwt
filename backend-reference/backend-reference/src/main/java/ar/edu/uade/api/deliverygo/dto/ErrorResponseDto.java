package ar.edu.uade.api.deliverygo.dto;
import java.time.LocalDateTime;
public record ErrorResponseDto(LocalDateTime timestamp, int status, String error, String mensaje, String path) {}
