package com.naoesqueceweslley.naoesqueceweslley.controller;

import com.naoesqueceweslley.naoesqueceweslley.dtos.LembreteRequestDTO;
import com.naoesqueceweslley.naoesqueceweslley.dtos.LembreteResponseDTO;
import com.naoesqueceweslley.naoesqueceweslley.enums.Prioridade;
import com.naoesqueceweslley.naoesqueceweslley.enums.Status;
import com.naoesqueceweslley.naoesqueceweslley.service.LembreteService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lembretes")
public class LembreteController {

    private final LembreteService lembreteService;

    public LembreteController(LembreteService lembreteService) {
        this.lembreteService = lembreteService;
    }

    @PostMapping
    public ResponseEntity<LembreteResponseDTO> cadastrar(@Valid @RequestBody LembreteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lembreteService.cadastrar(request));
    }

    // Passo 4 (Aula 07): Listagem geral com suporte a filtros opcionais por query params
    @GetMapping
    public ResponseEntity<List<LembreteResponseDTO>> listar(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Prioridade prioridade) {
        return ResponseEntity.ok(lembreteService.listarComFiltros(status, prioridade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.buscarPorId(id));
    }

    // Passo 4 (Aula 07): Endpoint para filtrar por intervalo de datas
    @GetMapping("/periodo")
    public ResponseEntity<List<LembreteResponseDTO>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(lembreteService.buscarPorPeriodo(inicio, fim));
    }

    // Passo 4 (Aula 07): Endpoint para listar lembretes pendentes atrasados
    @GetMapping("/atrasados")
    public ResponseEntity<List<LembreteResponseDTO>> listarAtrasados() {
        return ResponseEntity.ok(lembreteService.listarAtrasados());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LembreteRequestDTO request) {
        return ResponseEntity.ok(lembreteService.atualizar(id, request));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<LembreteResponseDTO> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.concluir(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        lembreteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}