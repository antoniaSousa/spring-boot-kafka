package io.github.antoniasousa.icompras.pedidos.service;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.antoniasousa.icompras.pedidos.repository.PedidoRepository;
import io.github.antoniasousa.icompras.pedidos.validador.PedidoValidator;
import io.github.antoniasousa.icompras.pedidos.client.ServicoBancarioClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;


   @Transactional
    public Pedido criarPedido(Pedido pedido) {
       realizarPersistencia(pedido);
       enviarSolicitacaoPagamento(pedido);
       return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        itemPedidoRepository.saveAll(pedido.getItens());
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        validator.validar(pedido);
        repository.save(pedido);
    }
}
