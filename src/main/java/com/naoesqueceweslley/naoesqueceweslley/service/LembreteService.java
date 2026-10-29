package com.naoesqueceweslley.naoesqueceweslley.service;

import com.naoesqueceweslley.naoesqueceweslley.Repository.LembreteRepository;
import com.naoesqueceweslley.naoesqueceweslley.dtos.LembreteRequestDTO;
import com.naoesqueceweslley.naoesqueceweslley.dtos.LembreteResponseDTO;
import com.naoesqueceweslley.naoesqueceweslley.enums.Status;
import com.naoesqueceweslley.naoesqueceweslley.exception.ResourceNotFoundException;
import com.naoesqueceweslley.naoesqueceweslley.model.Lembrete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LembreteService {

    private final LembreteRepository lembreteRepository;

    public LembreteService(LembreteRepository lembreteRepository) {
        this.lembreteRepository = lembreteRepository;
    }

    @Transactional
    public LembreteResponseDTO cadastrar(LembreteRequestDTO dto) {
        Lembrete lembrete = new Lembrete();
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataHora(dto.getDataHora());
        lembrete.setPrioridade(dto.getPrioridade());
        lembrete.setStatus(Status.PENDENTE);
        lembrete.setCriadoEm(LocalDateTime.now());
        lembrete.setAtualizadoEm(LocalDateTime.now());

        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    public List<LembreteResponseDTO> listarTodos() {
        List<Lembrete> lembretes = lembreteRepository.findAll();
        List<LembreteResponseDTO> dtos = new ArrayList<>();
        for (Lembrete l : lembretes) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }
        return dtos;
    }

    public LembreteResponseDTO buscarPorId(Long id) {
        return LembreteResponseDTO.fromEntity(obterEntidadePorId(id));
    }

    @Transactional
    public LembreteResponseDTO atualizar(Long id, LembreteRequestDTO dto) {
        Lembrete lembrete = obterEntidadePorId(id);
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataHora(dto.getDataHora());
        lembrete.setPrioridade(dto.getPrioridade());
        lembrete.setAtualizadoEm(LocalDateTime.now());

        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    @Transactional
    public LembreteResponseDTO concluir(Long id) {
        Lembrete lembrete = obterEntidadePorId(id);
        lembrete.setStatus(Status.CONCLUIDO);
        lembrete.setAtualizadoEm(LocalDateTime.now());

        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    @Transactional
    public void excluir(Long id) {
        lembreteRepository.delete(obterEntidadePorId(id));
    }

    private Lembrete obterEntidadePorId(Long id) {
        Optional<Lembrete> lembreteOpt = lembreteRepository.findById(id);
        if (lembreteOpt.isPresent()) {
            return lembreteOpt.get();
        } else {
            throw new ResourceNotFoundException("Lembrete não encontrado com ID: " + id);
        }
    }
}