package io.github.antoniasousa.icompras.pedidos.repository;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
