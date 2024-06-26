package com.example.TUBConnect.services;

import com.example.TUBConnect.models.Utilizador;
import com.example.TUBConnect.repositories.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UtilizadorService {
    private final UtilizadorRepository utilizadorRepository;

    @Autowired
    public UtilizadorService(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    public void addNewUser(Utilizador utilizador) {
        Optional<Utilizador> utilizadorOptional = utilizadorRepository.findUtilizadorByEmail(utilizador.getEmail());
        if (utilizadorOptional.isPresent()) {
            throw new IllegalStateException("Email já em utilização");
        }
        utilizadorRepository.save(utilizador);
    }

    public List<Utilizador> listarUtilizadores() {
        return utilizadorRepository.findAll();
    }

    public void apagarUtilizador(Long utilizadorId) {  // Alterado de utilizadorID para utilizadorId
        boolean existe = utilizadorRepository.existsById(utilizadorId);
        if (!existe) {
            throw new IllegalStateException("Utilizador com o id " + utilizadorId + " não existe");
        }
        utilizadorRepository.deleteById(utilizadorId);
    }

    @Transactional
    public void atualizarUtilizador(Long utilizadorId, String nome, String password, String tipo, String nacionalidade, String idiomaPreferido, String telefone) {
        Utilizador utilizador = utilizadorRepository.findById(utilizadorId).orElseThrow(() -> new IllegalStateException(
                "Utilizador com o id " + utilizadorId + " não existe"));

        if (nome != null && !nome.isEmpty() && !Objects.equals(utilizador.getNome(), nome)) {
            utilizador.setNome(nome);
        }
        if (password != null && !password.isEmpty() && !Objects.equals(utilizador.getPassword(), password)) {
            utilizador.setPassword(password);
        }
        if (tipo != null && !tipo.isEmpty() && !Objects.equals(utilizador.getTipo(), tipo)) {
            utilizador.setTipo(tipo);
        }
        if (nacionalidade != null && !nacionalidade.isEmpty() && !Objects.equals(utilizador.getNacionalidade(), nacionalidade)) {
            utilizador.setNacionalidade(nacionalidade);
        }
        if (idiomaPreferido != null && !idiomaPreferido.isEmpty() && !Objects.equals(utilizador.getIdiomaPreferido(), idiomaPreferido)) {
            utilizador.setIdiomaPreferido(idiomaPreferido);
        }
        if (telefone != null && !telefone.isEmpty() && !Objects.equals(utilizador.getTelefone(), telefone)) {
            utilizador.setTelefone(telefone);
        }

        utilizadorRepository.save(utilizador);
    }

    public Optional<Utilizador> login(String email, String password) {
        return utilizadorRepository.findByEmailAndPassword(email, password);
    }
}
