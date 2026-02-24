package com.clubdalulu.controle_estoque.produto.service;

import com.clubdalulu.controle_estoque.produto.domain.Produto;
import com.clubdalulu.controle_estoque.produto.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository){
        this.repository = repository;
    }

    @Transactional //protege (executa ou falha eh da um rollback
    public Produto criarProduto(Produto produto){ //produto = retorna(devolve o oeto salvo) o id e etc
        return repository.save(produto);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarProdutos(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosPorNome(String nome){
        if (nome == null || nome.isBlank()) {
            return repository.findAll();
        }
        return  repository.findByNomeContainingIgnoreCase(nome.trim());
    }

    @Transactional(readOnly = true) //so leitura, nao tem modificacao
    public Produto buscarProdutoPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public void deletarProduto(Long id){
        if (!repository.existsById(id)){
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public Produto atualizarProduto(Long id, Produto produtoAtualizado){
        Integer estoque = produtoAtualizado.getEstoque();
        if (estoque == null || estoque < 0) {
            throw new RuntimeException("Estoque não pode ser nulo ou negativo");
        }

        Produto produtoExistente = buscarProdutoPorId(id);
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setEstoque(estoque);

        return repository.save(produtoExistente);
    }
}
