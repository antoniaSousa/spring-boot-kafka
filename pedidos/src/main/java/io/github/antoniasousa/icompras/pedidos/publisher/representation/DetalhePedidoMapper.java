package io.github.antoniasousa.icompras.pedidos.publisher.representation;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.publisher.DetalhesPedidoRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "string")
public interface DetalhePedidoMapper {

    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "codigoCliente", target = "codigoCliente")
    @Mapping(source = "codigoCliente.nome", target = "nome")
    @Mapping(source = "dadosCliente.cpf", target = "cpf")
    @Mapping(source = "dadosCliente.logradouro", target = "logradouro")
    @Mapping(source = "dadosCliente.numero", target = "numero")
    @Mapping(source = "dadosCliente.bairro", target = "bairro")
    @Mapping(source = "dadosCliente.email", target = "email")
    @Mapping(source = "dadosCliente.telefone", target = "telefone")
    @Mapping(source = "dataPedido", target = "dataPedido", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "itens", target = "itens")
    DetalhesPedidoRepresentation map(Pedido pedido);
}
