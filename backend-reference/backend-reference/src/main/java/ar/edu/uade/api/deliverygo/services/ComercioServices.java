package ar.edu.uade.api.deliverygo.services;

import ar.edu.uade.api.deliverygo.dto.ComercioDto;
import ar.edu.uade.api.deliverygo.excepcion.*;
import ar.edu.uade.api.deliverygo.model.Comercio;
import ar.edu.uade.api.deliverygo.repository.ComercioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class ComercioServices {
    private final ComercioRepository comercioRepository;
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public ComercioServices(ComercioRepository r) {
        this.comercioRepository = r;
    }

    @Transactional
    public ComercioDto crearComercio(ComercioDto dto) {
        validarCuit(dto.cuitComercio());
        validarNombre(dto.nombreComercio());
        validarEmail(dto.emailComercio());
        if (comercioRepository.findByCuit(dto.cuitComercio()).isPresent())
            throw new RecursoDuplicadoException("El CUIT ya existe: " + dto.cuitComercio());
        Comercio c = comercioRepository.save(new Comercio(dto.nombreComercio(), dto.cuitComercio(), dto.emailComercio(), true));
        return toDto(c);
    }

    @Transactional(readOnly = true)
    public List<ComercioDto> buscarTodos() {
        return comercioRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ComercioDto buscarPorCuit(String cuit) {
        return toDto(buscarEntidadPorCuit(cuit));
    }

    @Transactional
    public ComercioDto modificarComercio(String cuit, ComercioDto dto) {
        Comercio c = buscarEntidadPorCuit(cuit);
        validarNombre(dto.nombreComercio());
        validarEmail(dto.emailComercio());
        c.setNombre(dto.nombreComercio());
        c.setEmail(dto.emailComercio());
        return toDto(c); // dirty checking
    }

    @Transactional
    public void desactivar(String cuit) {
        Comercio c = buscarEntidadPorCuit(cuit);
        if (!c.getActivo()) throw new ReglaNegocioException("El comercio ya se encuentra inactivo");
        c.setActivo(false);
    }

    private Comercio buscarEntidadPorCuit(String cuit) {
        return comercioRepository.findByCuit(cuit).orElseThrow(() -> new RecursoNoEncontradoException("No existe comercio con CUIT " + cuit));
    }

    private void validarCuit(String v) {
        if (v == null || v.isBlank()) throw new ReglaNegocioException("El CUIT es obligatorio");
    }

    private void validarNombre(String v) {
        if (v == null || v.isBlank()) throw new ReglaNegocioException("El nombre es obligatorio");
    }

    private void validarEmail(String v) {
        if (v == null || !EMAIL.matcher(v).matches()) throw new ReglaNegocioException("Formato de email invalido");
    }

    private ComercioDto toDto(Comercio c) {
        return new ComercioDto(c.getNombre(), c.getCuit(), c.getEmail());
    }
}
