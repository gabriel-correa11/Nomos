package br.com.nomos.service;

import br.com.nomos.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    CandidatoRepository candidatoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("\n================ [DEBUG LOGIN] 1. AuthorizationService ================");
        System.out.println("Tentando carregar usuário com email: " + username);

        var candidato = candidatoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username));

        System.out.println("Usuário encontrado no banco: " + candidato.getUsername());
        System.out.println("Senha criptografada (HASH NO BANCO): " + candidato.getPassword());
        System.out.println("======================================================================");

        return candidato;
    }
}