package io.github.antoniasousa.icompras.pedidos.publisher.representation;

import io.github.antoniasousa.icompras.pedidos.model.StatusPedido;


import java.math.BigDecimal;
import java.util.List;

public record DetalhesPedidoRepresentation(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        StatusPedido statusPedido,
        List<DetalheItensRepresentation> itens
) {
}
