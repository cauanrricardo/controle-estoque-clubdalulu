package com.clubdalulu.controle_estoque.web.form;


public class ProdutoForm {

    private Long id;
    private String nome;
    private Integer estoque;

    public ProdutoForm(Long id, String nome, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
    }

    public Long getId() { return id; }
    public void setId(Long id) {this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
}