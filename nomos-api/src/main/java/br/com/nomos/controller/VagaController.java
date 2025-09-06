package br.com.nomos.controller;

import br.com.nomos.model.Vaga;
import br.com.nomos.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @PostMapping
    public ResponseEntity<Vaga> criarVaga(@RequestBody Vaga vaga) {
        Vaga vagaSalva = vagaRepository.save(vaga);
        return new ResponseEntity<>(vagaSalva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaRepository.findAll();
        return ResponseEntity.ok(vagas);
    }
}