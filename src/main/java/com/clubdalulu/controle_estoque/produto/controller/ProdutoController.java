package com.clubdalulu.controle_estoque.produto.controller;


import com.clubdalulu.controle_estoque.produto.domain.Produto;
import com.clubdalulu.controle_estoque.produto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")

public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto){
        Produto criado = service.criarProduto(produto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoporId(@PathVariable Long id){
        Produto produto = service.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarOuBuscarProdutos (@RequestParam(required = false) String nome){
        List<Produto> produtos = service.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> deletarProduto(@PathVariable Long id){
        service.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto (@PathVariable Long id, @RequestBody Produto produto){
        Produto atualizado = service.atualizarProduto(id, produto);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping("/{id}/entrada")
    public ResponseEntity<Produto> atualizarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        Produto atualizado = service.entradaEstoque(id, quantidade);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping("/{id}/saida")
    public ResponseEntity<Produto> saidaEstoque(@PathVariable Long id, @RequestParam Integer quantidade){
        Produto atualizado = service.saidaEstoque(id, quantidade);
        return ResponseEntity.ok(atualizado);
    }

}
