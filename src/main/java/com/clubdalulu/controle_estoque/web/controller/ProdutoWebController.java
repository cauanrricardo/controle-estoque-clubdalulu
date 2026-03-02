package com.clubdalulu.controle_estoque.web.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.clubdalulu.controle_estoque.produto.domain.Produto;
import com.clubdalulu.controle_estoque.web.form.ProdutoForm;
import com.clubdalulu.controle_estoque.web.service.ProdutoWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/produtos")
public class ProdutoWebController {

    private final ProdutoWebService webService;

    public ProdutoWebController(ProdutoWebService webService) {
        this.webService = webService;
    }

    @GetMapping
    public String telaProdutos(@RequestParam(required = false) String nome, @RequestParam(required = false) Long editId, Model model) {
        List<Produto> produtos = webService.listarOuBuscar(nome);

        ProdutoForm form = new ProdutoForm(null, "", 0);
        if (editId != null) {
            Produto p = webService.buscarPorId(editId);
            form = new ProdutoForm(p.getId(), p.getNome(), p.getEstoque());
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("nomeBusca", nome == null ? "" : nome);
        model.addAttribute("form", form);
        return "produtos";
    }

    @PostMapping
    public String salvarProduto(@ModelAttribute("form") ProdutoForm form, RedirectAttributes redirectAttributes) {
        try {
            webService.salvar(form);
            redirectAttributes.addFlashAttribute("sucesso", "Produto salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/web/produtos";
    }

    @PostMapping("/{id}/delete")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            webService.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/web/produtos";
    }

    @PostMapping("/{id}/entrada")
    public String entrada(@PathVariable Long id, @RequestParam Integer quantidade, RedirectAttributes redirectAttributes) {
        try {
            webService.entrada(id, quantidade);
            redirectAttributes.addFlashAttribute("sucesso", "Entrada realizada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/web/produtos";
    }

    @PostMapping("/{id}/saida")
    public String saida(@PathVariable Long id, @RequestParam Integer quantidade, RedirectAttributes redirectAttributes) {
        try {
            webService.saida(id, quantidade);
            redirectAttributes.addFlashAttribute("sucesso", "Saída realizada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/web/produtos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id) {
        return "redirect:/web/produtos?editId=" + id;
    }

    @GetMapping("/{id}/movimentacoes")
    public String movimentacoes(@PathVariable Long id, Model model) {

        Produto produto = webService.buscarPorId(id);
        var movimentacoes = webService.listarMovimentacoes(id);

        model.addAttribute("produto", produto);
        model.addAttribute("movimentacoes", movimentacoes);

        return "movimentacoes";
    }
}