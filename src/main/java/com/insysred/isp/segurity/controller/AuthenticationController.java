package com.insysred.isp.segurity.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.insysred.isp.repository.UsuarioRepository;
import com.insysred.isp.segurity.dto.TokenDto;
import com.insysred.isp.segurity.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insysred.isp.segurity.dto.ResponseMessageDto;
import com.insysred.isp.segurity.dto.SigninClienteRequest;
import com.insysred.isp.segurity.dto.SigninRequest;
import com.insysred.isp.segurity.security.jwt.JwtUtils;
import com.insysred.isp.segurity.service.IClienteAuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IClienteAuthService clienteAuthService;


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody SigninRequest loginRequest, HttpServletResponse response) throws IOException {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    System.out.println(loginRequest);
    // Si la autenticación es exitosa, genera un token JWT
    String token = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
    Optional<Usuario> usuario = usuarioRepository.findByUsername(loginRequest.getUsername());
    LocalDateTime horaActual = LocalDateTime.now();

    // Sumamos 2 horas a la hora actual
    LocalDateTime nuevaHora = horaActual.plusHours(2);

    // Configura la respuesta con el token JWT y otra información
    TokenDto tokenDto = new TokenDto();
    tokenDto.setAccess_token(token);
    tokenDto.setToken_type("Bearer");
    tokenDto.setExp(2L);
    tokenDto.setExpires_in(2);
    tokenDto.setRefresh_token("");
    tokenDto.setIdUsuario(usuario.get().getId());
    tokenDto.setIdEmpleado(usuario.get().getIdEmpleado());
    tokenDto.setEsPrimerIngreso(usuario.get().getEsPrimerInicio());
    tokenDto.setUsername(usuario.get().getUsername());
    /*Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put("access_token", token);
    httpResponse.put("token_type", "Bearer");
    httpResponse.put("exp", 2);
    httpResponse.put("expires_in", 2);
    httpResponse.put("refresh_token", null);
*/
    // Agrega el token a la cabecera de autorización de la respuesta
    response.addHeader("Authorization", "Bearer " + token);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.OK.value());
    response.getWriter().write(new ObjectMapper().writeValueAsString(tokenDto));
    response.getWriter().flush();

    return ResponseEntity.ok().build();
  }

    @PostMapping("/signin/cliente")
    public ResponseEntity<?> signinCliente(@RequestBody SigninClienteRequest request) {
    	try {
    		return ResponseEntity.ok(clienteAuthService.signinCliente(request));
    	} catch (Exception e) {
    		return new ResponseEntity<>(new ResponseMessageDto(e.getMessage()), HttpStatus.UNAUTHORIZED);
    	}
    }
}
