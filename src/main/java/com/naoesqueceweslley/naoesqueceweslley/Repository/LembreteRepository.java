package com.naoesqueceweslley.naoesqueceweslley.Repository;


import com.naoesqueceweslley.naoesqueceweslley.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
}