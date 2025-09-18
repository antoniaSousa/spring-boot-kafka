package io.github.antoniasousa.icompras.pedidos.repository;

import io.github.antoniasousa.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
