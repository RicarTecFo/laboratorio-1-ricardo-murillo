package com.labricardomurillo.lab1.service;

import com.labricardomurillo.lab1.dto.LoginRequestDTO;
import com.labricardomurillo.lab1.dto.LoginResponseDTO;
import com.labricardomurillo.lab1.dto.RegistroRequestDTO;
import com.labricardomurillo.lab1.dto.RegistroResponseDTO;
import com.labricardomurillo.lab1.model.Usuario;
import com.labricardomurillo.lab1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final RestClient restClient = RestClient.create();
    private final String tokenUri;
    private final String clientId;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(@Value("${keycloak.token-uri}") String tokenUri,
                       @Value("${keycloak.client-id}") String clientId, UsuarioRepository usuarioRepository) {
        this.tokenUri = tokenUri;
        this.clientId = clientId;
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("username", request.username());
        form.add("password", request.password());

        try {
            return restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .body(LoginResponseDTO.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }
    }

    public RegistroResponseDTO registro(RegistroRequestDTO request) {
        Usuario usuario = new Usuario();

        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setEmail(request.email());

        Usuario creado = usuarioRepository.save(usuario);
        return new RegistroResponseDTO(creado.getId(), creado.getUsername(), creado.getEmail());
    }
}