package br.com.nomos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "candidatos")
@Getter
@Setter
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    private String formacao;
    private String oab; // Ex: "MT/12345" ou apenas o número
    private String areasDeInteresse;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String apresentacao;

}