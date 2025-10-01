package io.github.antoniasousa.icompras.pedidos.publisher.representation;

import java.math.BigDecimal;

public record DetalheItensRepresentation(
        Long codigoPedido,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario
) {
    public BigDecimal getTotal() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
