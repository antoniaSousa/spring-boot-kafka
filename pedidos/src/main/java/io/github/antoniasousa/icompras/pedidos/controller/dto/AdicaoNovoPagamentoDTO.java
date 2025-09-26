package io.github.antoniasousa.icompras.pedidos.controller.dto;

import io.github.antoniasousa.icompras.pedidos.model.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dadosCartao, TipoPagamento tipo) {

}
