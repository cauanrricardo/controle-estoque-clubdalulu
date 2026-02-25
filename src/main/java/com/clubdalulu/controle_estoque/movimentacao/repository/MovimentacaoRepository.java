package com.clubdalulu.controle_estoque.movimentacao.repository;

import com.clubdalulu.controle_estoque.movimentacao.domain.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    void deleteByProdutoId(Long produtoId);
    List<MovimentacaoEstoque> findByProdutoIdOrderByDataHoraDesc(Long produtoId);
}