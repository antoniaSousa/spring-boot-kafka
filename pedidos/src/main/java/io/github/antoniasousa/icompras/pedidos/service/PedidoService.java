package io.github.antoniasousa.icompras.pedidos.service;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.antoniasousa.icompras.pedidos.repository.PedidoRepository;
import io.github.antoniasousa.icompras.pedidos.validador.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;

    public Pedido criarPedido(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }
}
