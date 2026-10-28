package com.naoesqueceweslley.naoesqueceweslley.controller;


import com.naoesqueceweslley.naoesqueceweslley.enums.Status;
import com.naoesqueceweslley.naoesqueceweslley.model.Lembrete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/lembretes")
public class LembreteController {

    private final List<Lembrete> lembretes = new ArrayList<>();
    private final AtomicLong geradorId = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<Lembrete> cadastrar(@RequestBody Lembrete lembrete) {
        lembrete.setId(geradorId.getAndIncrement());
        if (lembrete.getStatus() == null) {
            lembrete.setStatus(Status.PENDENTE);
        }
        lembretes.add(lembrete);
        return ResponseEntity.status(HttpStatus.CREATED).body(lembrete);
    }

    @GetMapping
    public ResponseEntity<List<Lembrete>> listar() {
        return ResponseEntity.ok(lembretes);
    }
}