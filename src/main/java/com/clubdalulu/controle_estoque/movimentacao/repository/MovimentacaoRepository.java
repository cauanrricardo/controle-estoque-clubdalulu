package com.clubdalulu.controle_estoque.movimentacao.repository;

import com.clubdalulu.controle_estoque.movimentacao.domain.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, Long> {
}
