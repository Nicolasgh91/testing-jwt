package ar.edu.uade.api.deliverygo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

  /*
   * IMPORTANTE:
   * Esta clave está hardcodeada únicamente con fines didácticos.
   *
   * En un ambiente real debería obtenerse desde:
   * - variable de entorno
   * - application.properties / application.yml
   * - Secret Manager / Vault
   */
  private static final String SECRET_KEY =
          "DeliveryGo-Clave-Secreta-JWT-2026-UADE-API";

  /*
   * Tiempo de expiración del token.
   * 60 minutos expresados en milisegundos.
   */
  private static final long EXPIRATION_TIME =
          60 * 60 * 1000L;

  /**
   * Genera un JWT para un usuario autenticado.
   *
   * @param username usuario autenticado
   * @param rol      rol del usuario: ADMIN, COMERCIO, USER, etc.
   * @return JWT firmado
   */
  public String generarToken(
          String username,
          String rol) {

    Date fechaCreacion = new Date();

    Date fechaExpiracion =
            new Date(
                    fechaCreacion.getTime()
                            + EXPIRATION_TIME
            );

    return Jwts.builder()

            // Identifica al usuario
            .subject(username)

            // Información adicional del JWT
            .claim("role", rol)

            // Fecha de creación
            .issuedAt(fechaCreacion)

            // Fecha de vencimiento
            .expiration(fechaExpiracion)

            // Firma digital
            .signWith(obtenerClave())

            // Genera el String JWT
            .compact();
  }

  /**
   * Obtiene el username almacenado
   * en el campo "sub" del JWT.
   */
  public String obtenerUsername(
          String token) {

    return obtenerClaims(token)
            .getSubject();
  }

  /**
   * Obtiene el rol almacenado
   * dentro del JWT.
   */
  public String obtenerRol(
          String token) {

    return obtenerClaims(token)
            .get(
                    "role",
                    String.class
            );
  }

  /**
   * Valida el token.
   *
   * Si:
   * - la firma es incorrecta,
   * - el token está vencido,
   * - el formato es inválido,
   *
   * JJWT lanza una excepción.
   */
  public boolean validarToken(
          String token) {

    obtenerClaims(token);

    return true;
  }

  /**
   * Devuelve el tiempo de expiración
   * expresado en segundos.
   *
   * Es utilizado por AuthController
   * para construir TokenDto.
   */
  public long getExpirationSeconds() {

    return EXPIRATION_TIME / 1000;
  }

  /**
   * Obtiene todos los Claims del JWT.
   *
   * Además verifica automáticamente
   * la firma y la expiración.
   */
  private Claims obtenerClaims(
          String token) {

    return Jwts.parser()
            .verifyWith(obtenerClave())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  /**
   * Construye la clave criptográfica
   * utilizada para firmar y verificar
   * el JWT.
   */
  private SecretKey obtenerClave() {

    return Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(
                    StandardCharsets.UTF_8
            )
    );
  }
}