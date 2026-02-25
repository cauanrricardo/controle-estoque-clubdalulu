package com.clubdalulu.controle_estoque.web.service;

import com.clubdalulu.controle_estoque.movimentacao.domain.MovimentacaoEstoque;
import com.clubdalulu.controle_estoque.movimentacao.repository.MovimentacaoRepository;
import com.clubdalulu.controle_estoque.produto.domain.Produto;
import com.clubdalulu.controle_estoque.produto.service.ProdutoService;
import com.clubdalulu.controle_estoque.web.form.ProdutoForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoWebService {

    private final ProdutoService produtoService;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoWebService(ProdutoService produtoService, MovimentacaoRepository movimentacaoRepository) {

        this.produtoService = produtoService;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<Produto> listarOuBuscar(String nome) {
        return produtoService.buscarProdutosPorNome(nome);
    }

    public Produto buscarPorId(Long id) {
        return produtoService.buscarProdutoPorId(id);
    }

    public Produto salvar(ProdutoForm form) {
        Produto p = new Produto();
        p.setNome(form.getNome());
        p.setEstoque(form.getEstoque());

        if (form.getId() == null) {
            return produtoService.criarProduto(p);
        }
        return produtoService.atualizarProduto(form.getId(), p);
    }

    public void deletar(Long id) {
        produtoService.deletarProduto(id);
    }

    public Produto entrada(Long id, Integer quantidade) {
        return produtoService.entradaEstoque(id, quantidade);
    }

    public Produto saida(Long id, Integer quantidade) {
        return produtoService.saidaEstoque(id, quantidade);
    }

    public List<MovimentacaoEstoque> listarMovimentacoes(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataHoraDesc(produtoId);
    }
}