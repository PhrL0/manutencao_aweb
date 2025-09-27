package com.manutencao.aweb.manutencao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manutencao.aweb.manutencao.model.Solicitacao;

public interface SolicitacaoRepository  extends JpaRepository<Solicitacao,Long>{
    
}
