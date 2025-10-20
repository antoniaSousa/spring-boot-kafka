package io.github.antoniasousa.icompras.pedidos.controller.dto;


import java.math.BigDecimal;

public record ItemPedidoDTO(
        Long codigoProduto, Integer quantidade, BigDecimal valorUnitario, String bairro, String cidade, String estado, String observacao
) {
}
