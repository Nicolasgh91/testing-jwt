package ar.edu.uade.api.deliverygo.controller;

import ar.edu.uade.api.deliverygo.dto.*;
import ar.edu.uade.api.deliverygo.services.ComercioServices;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/comercios")
@Tag(name = "Comercios", description = "CRUD del recurso Comercio")
public class ComercioController implements IComercioController {
    private final ComercioServices service;

    public ComercioController(ComercioServices s) {
        service = s;
    }

    @PostMapping
    public ResponseEntity<ComercioDto> crear(@RequestBody ComercioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearComercio(dto));
    }

    @Operation(summary = "Listar comercios", description = "Consulta publica")
    @ApiResponse(responseCode = "200", description = "Listado")
    @GetMapping
    public ResponseEntity<List<ComercioDto>> listar() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @Operation(summary = "Buscar comercio por CUIT")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Encontrado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/{cuit}")
    public ResponseEntity<ComercioDto> buscar(@Parameter(example = "20-12345678-9") @PathVariable String cuit) {
        return ResponseEntity.ok(service.buscarPorCuit(cuit));
    }

    @Operation(summary = "Modificar comercio", description = "Actualiza nombre/email mediante dirty checking. Requiere ADMIN o COMERCIO.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "401", description = "No autenticado"), @ApiResponse(responseCode = "403", description = "Sin permiso"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PutMapping("/{cuit}")
    public ResponseEntity<ComercioDto> modificar(@PathVariable String cuit, @RequestBody ComercioDto dto) {
        return ResponseEntity.ok(service.modificarComercio(cuit, dto));
    }

    @Operation(summary = "Desactivar comercio", description = "Baja logica. SOLO ROLE_ADMIN.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Desactivado"), @ApiResponse(responseCode = "401", description = "JWT ausente/invalido"), @ApiResponse(responseCode = "403", description = "Autenticado pero no ADMIN"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @DeleteMapping("/{cuit}")
    public ResponseEntity<Void> eliminar(@PathVariable String cuit) {
        service.desactivar(cuit);
        return ResponseEntity.noContent().build();
    }
}
