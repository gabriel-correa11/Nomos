package br.com.nomos.controller;

import br.com.nomos.dto.LoginRequestDTO;
import br.com.nomos.model.Candidato;
import br.com.nomos.repository.CandidatoRepository;
import br.com.nomos.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registrarCandidato(@RequestBody Candidato candidato) {
        String senhaPura = candidato.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaPura);
        candidato.setSenha(senhaCriptografada);

        System.out.println("\n================ [DEBUG CADASTRO] ================");
        System.out.println("Email para cadastrar: " + candidato.getEmail());
        System.out.println("Senha Pura enviada: " + senhaPura);
        System.out.println("Senha Criptografada (HASH GERADO): " + senhaCriptografada);
        System.out.println("==============================================");

        candidatoRepository.save(candidato);
        return new ResponseEntity<>("Candidato registrado com sucesso!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((Candidato) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    // NOVO ENDPOINT DE DEBUG MANUAL
    @PostMapping("/login/debug")
    public ResponseEntity<String> loginDebug(@RequestBody LoginRequestDTO data) {
        System.out.println("\n--- [DEBUG LOGIN MANUAL] 2. AuthController ---");
        System.out.println("Recebido para login: email='" + data.email() + "', senha='" + data.senha() + "'");

        Optional<Candidato> candidatoOptional = candidatoRepository.findByEmail(data.email());

        if (candidatoOptional.isEmpty()) {
            System.out.println("RESULTADO: Usuário não encontrado no banco.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Candidato candidato = candidatoOptional.get();
        System.out.println("Usuário encontrado: " + candidato.getUsername());
        System.out.println("Hash da senha no banco: " + candidato.getPassword());

        boolean senhasBatem = passwordEncoder.matches(data.senha(), candidato.getPassword());
        System.out.println("--> Resultado da comparação (passwordEncoder.matches): " + senhasBatem + " <--");

        if (senhasBatem) {
            System.out.println("DIAGNÓSTICO: SUCESSO! Senhas batem. O problema está no fluxo do Spring.");
            return ResponseEntity.ok("Debug: As senhas batem!");
        } else {
            System.out.println("DIAGNÓSTICO: FALHA! As senhas NÃO batem. O problema está na geração ou comparação do hash.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Debug: Credenciais inválidas.");
        }
    }
}