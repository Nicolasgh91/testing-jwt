package ar.edu.uade.api.deliverygo.controller;

import ar.edu.uade.api.deliverygo.dto.ComercioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IComercioController {

    @Operation(summary = "Crear comercio", description = "Registra un comercio. Requiere ADMIN o COMERCIO.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos invalidos"), @ApiResponse(responseCode = "401", description = "No autenticado"), @ApiResponse(responseCode = "403", description = "Sin permiso"), @ApiResponse(responseCode = "409", description = "CUIT duplicado")})
    ResponseEntity<ComercioDto> crear(ComercioDto dto);
}
