package io.github.antoniasousa.icompras.pedidos.client;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ServicoBancarioClient {

    public String solicitarPagamento(Pedido pedido) {
        log.info("Solicitando pagamento de pedido {}", pedido.getCodigo());
        return UUID.randomUUID().toString();
    }
}
