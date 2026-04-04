package com.clubdalulu.controle_estoque.produto.service;

import com.clubdalulu.controle_estoque.movimentacao.domain.MovimentacaoEstoque;
import com.clubdalulu.controle_estoque.movimentacao.domain.TipoMovimentacao;
import com.clubdalulu.controle_estoque.movimentacao.repository.MovimentacaoRepository;
import com.clubdalulu.controle_estoque.produto.domain.Produto;
import com.clubdalulu.controle_estoque.produto.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.clubdalulu.controle_estoque.shared.exception.BadRequestException;
import com.clubdalulu.controle_estoque.shared.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoService(ProdutoRepository repository, MovimentacaoRepository movimentacaoRepository) {
        this.repository = repository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional //protege (executa ou falha eh da um rollback
    public Produto criarProduto(Produto produto) {//produto = retorna(devolve o objeto salvo) o id e etc
        Produto salvo = repository.save(produto);

        if (salvo.getEstoque() != null && salvo.getEstoque() > 0) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setProduto(salvo);
            mov.setTipo(TipoMovimentacao.ENTRADA);
            mov.setQuantidade(salvo.getEstoque());
            mov.setDataHora(LocalDateTime.now());
            movimentacaoRepository.save(mov);
        }

        return salvo;
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
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    @Transactional
    public void deletarProduto(Long id){
        if (!repository.existsById(id)){
            throw new NotFoundException("Produto não encontrado");
        }
        movimentacaoRepository.deleteByProdutoId(id);

        repository.deleteById(id);
    }

    @Transactional
    public Produto atualizarProduto(Long id, Produto produtoAtualizado){
        Integer estoque = produtoAtualizado.getEstoque();
        if (estoque == null || estoque < 0) {
            throw new BadRequestException("Estoque não pode ser nulo ou negativo");
        }

        Produto produtoExistente = buscarProdutoPorId(id);
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setEstoque(estoque);

        return repository.save(produtoExistente);
    }

    @Transactional
    public Produto entradaEstoque(Long id, Integer quantidade){
       if(quantidade == null || quantidade <= 0){
           throw new BadRequestException("Quantidade deve ser maior que 0");
       }
       Produto produto = buscarProdutoPorId(id);
       produto.setEstoque(produto.getEstoque() + quantidade);

        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setProduto(produto);
        mov.setTipo(TipoMovimentacao.ENTRADA);
        mov.setQuantidade(quantidade);
        mov.setDataHora(LocalDateTime.now());
        movimentacaoRepository.save(mov);

       return repository.save(produto);
    }

    @Transactional
    public Produto saidaEstoque(Long id, Integer quantidade){
        if(quantidade == null || quantidade <= 0){
            throw new BadRequestException("Quantidade deve ser maior que 0");
        }
        Produto produto = buscarProdutoPorId(id);
        if (produto.getEstoque() < quantidade) {
            throw new BadRequestException("Estoque insuficiente para a saída");
        }
        produto.setEstoque(produto.getEstoque() - quantidade);

        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setProduto(produto);
        mov.setTipo(TipoMovimentacao.SAIDA);
        mov.setQuantidade(quantidade);
        mov.setDataHora(LocalDateTime.now());
        movimentacaoRepository.save(mov);

        return repository.save(produto);
    }
}
