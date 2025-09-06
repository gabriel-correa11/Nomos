package br.com.nomos.controller;

import br.com.nomos.model.Candidato;
import br.com.nomos.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registrarCandidato(@RequestBody Candidato candidato) {
        // Criptografa a senha antes de salvar
        candidato.setSenha(passwordEncoder.encode(candidato.getSenha()));

        candidatoRepository.save(candidato);

        return new ResponseEntity<>("Candidato registrado com sucesso!", HttpStatus.CREATED);
    }
}