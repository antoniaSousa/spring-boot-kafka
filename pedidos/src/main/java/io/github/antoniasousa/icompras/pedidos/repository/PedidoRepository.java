package io.github.antoniasousa.icompras.pedidos.repository;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
   Optional<Pedido> findByCodigoAndChavePagamento(Long codigo, String chavePagamento);

    void carregarDadosCliente( Pedido pedido);

    void carregarItensPedido(Pedido pedido);
}
