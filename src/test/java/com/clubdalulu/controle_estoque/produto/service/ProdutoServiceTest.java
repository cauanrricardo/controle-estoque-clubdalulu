package com.clubdalulu.controle_estoque.produto.service;

import com.clubdalulu.controle_estoque.movimentacao.domain.MovimentacaoEstoque;
import com.clubdalulu.controle_estoque.movimentacao.repository.MovimentacaoRepository;
import com.clubdalulu.controle_estoque.produto.domain.Produto;
import com.clubdalulu.controle_estoque.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Nested
    @DisplayName("Testes para entrada e saída de estoque")
    class EntradaSaidaEstoqueTest {

        @Test
        @DisplayName("Deve adicionar quantidade ao estoque corretamente")
        void deveAdicionarQuantidadeAoEstoque() {

            Long id = 1L;
            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome("Shampoo Glow");
            produto.setEstoque(10);

            when(produtoRepository.findById(id))
                    .thenReturn(Optional.of(produto));
            when(produtoRepository.save(any(Produto.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));
            Produto resultado = produtoService.entradaEstoque(id, 5);

            assertEquals(15, resultado.getEstoque());
            verify(produtoRepository).save(any(Produto.class));
            verify(movimentacaoRepository).save(any(MovimentacaoEstoque.class));

        }

        @Test
        @DisplayName("Saída válida subtrai e salva movimentação")
        void deveSubtrairQuantidadeDoEstoqueERegistrarMovimentacao() {
            Long id = 1L;
            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome("Shampoo Glow");
            produto.setEstoque(10);

            when(produtoRepository.findById(id))
                    .thenReturn(Optional.of(produto));
            when(produtoRepository.save(any(Produto.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Produto resultado = produtoService.saidaEstoque(id, 3);

            assertEquals(7, resultado.getEstoque());
            verify(produtoRepository).save(any(Produto.class));
            verify(movimentacaoRepository).save(any(MovimentacaoEstoque.class));
        }

        @Test
        @DisplayName("Saída com estoque insuficiente lança exception e não salva nada")
        void saidaComEstoqueInsuficienteLancaException() {
            Long id = 1L;
            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome("Shampoo Glow");
            produto.setEstoque(2);

            when(produtoRepository.findById(id))
                    .thenReturn(Optional.of(produto));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                produtoService.saidaEstoque(id, 3);
            });

            assertEquals("Estoque insuficiente para a saída", exception.getMessage());
            verify(produtoRepository).findById(id);
            verify(produtoRepository, never()).save(any(Produto.class));
            verify(movimentacaoRepository, never()).save(any(MovimentacaoEstoque.class));

        }

        @Test
        @DisplayName("Entrada com quantidade <= 0 lança exception e não chama o banco")
        void entradaComQuantidadeInvalidaLancaException() {

            Long id = 1L;

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                produtoService.entradaEstoque(id, 0);
            });

            assertEquals("Quantidade deve ser maior que 0", exception.getMessage());

            verify(produtoRepository, never()).findById(any());
            verify(produtoRepository, never()).save(any());
            verify(movimentacaoRepository, never()).save(any());
        }

        @Test
        @DisplayName("Produto não encontrado lança exception")
        void produtoNaoEncontradoLancaException() {
            Long id = 1L;

            when(produtoRepository.findById(id))
                    .thenReturn(Optional.empty());

            RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
                produtoService.entradaEstoque(id, 5);
            });

            assertEquals("Produto não encontrado", exception.getMessage());
            verify(produtoRepository).findById(id);
            verify(produtoRepository, never()).save(any(Produto.class));
            verify(movimentacaoRepository, never()).save(any(MovimentacaoEstoque.class));
        }


    }
}
