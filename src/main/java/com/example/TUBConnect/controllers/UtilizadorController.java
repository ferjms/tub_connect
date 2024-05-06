package com.example.TUBConnect.controllers;

import com.example.TUBConnect.models.Utilizador;

import com.example.TUBConnect.repositories.UtilizadorRepository;
import com.example.TUBConnect.services.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tub")
public class UtilizadorController {

    private final UtilizadorService utilizadorService;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    public UtilizadorController(UtilizadorService utilizadorService) {
        this.utilizadorService = utilizadorService;
    }

    @GetMapping("/user/listar")
    public List<Utilizador> listarUtilizadores() {
        return utilizadorService.listarUtilizadores();
        }

    @PostMapping("/user/registar")
    public void criarUtilizador(@RequestBody Utilizador utilizador) {
        utilizadorService.addNewUser(utilizador);
    }

    @DeleteMapping(path = "/user/{utilizadorID}")
    public void apagarUtilizador(@PathVariable("utilizadorID")Long utilizadorID){
        utilizadorService.apagarUtilizador(utilizadorID);
    }


    @PutMapping(path = "{utilizadorID}")
    public void atualizarUtilizador(
            @PathVariable("utilizadorID")Long utilizadorID,
            @RequestParam(required = false)String nome,
            @RequestParam(required = false)String password,
            @RequestParam(required = false)String tipo,
            @RequestParam(required = false)String nacionalidade,
            @RequestParam(required = false)String idiomaPreferido,
            @RequestParam(required = false)String telefone ) {
            utilizadorService.atualizarUtilizador(utilizadorID,nome,password,tipo,nacionalidade,idiomaPreferido,telefone);

    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody Utilizador loginRequest) {
        return utilizadorService.login(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Login successful!");
                    response.put("tipoUtilizador", user.getTipo()); // "turista" ou "cliente"
                    return ResponseEntity.ok().body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("mensagem de erro", "Credenciais inválidas")));
    }

    @PostMapping("/user/save")
    public Utilizador save(@RequestBody Utilizador utilizador){
        return utilizadorRepository.save(utilizador);
    }


}