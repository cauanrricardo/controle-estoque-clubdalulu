package com.clubdalulu.controle_estoque.produto.repository;

import com.clubdalulu.controle_estoque.produto.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//jpa ja te dá alguns metodos prontos

public interface ProdutoRepository  extends JpaRepository<Produto, Long> {
    List<Produto>findByNomeContainingIgnoreCase(String nome);
}
