package io.github.antoniasousa.icompras.pedidos.controller.mappers;

import io.github.antoniasousa.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.antoniasousa.icompras.pedidos.model.ItemPedido;
import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.model.StatusPedido;
import io.github.antoniasousa.icompras.pedidos.publisher.representation.DetalhesPedidoRepresentation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);
    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    Pedido map(DetalhesPedidoRepresentation dto);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> dtos) {
        return dtos.stream().map(ITEM_PEDIDO_MAPPER::map).toList();

    }
    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = calcularTotal(pedido);

        pedido.setTotal(total);
        pedido.getItens().forEach( item -> item.setPedido(pedido));
        }
        private static BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItens().stream().map(item ->
                item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()) )
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();

        }

    }

