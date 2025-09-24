package io.github.antoniasousa.icompras.pedidos.controller.dto;

public record RecebimentoCallbackPagamentoDTO(
        Long codigo, String chavePagamento, boolean status, String observacao
) {
}
