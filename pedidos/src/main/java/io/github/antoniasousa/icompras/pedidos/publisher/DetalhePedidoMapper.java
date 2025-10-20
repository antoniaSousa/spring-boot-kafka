package io.github.antoniasousa.icompras.pedidos.publisher;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.publisher.representation.DetalhesPedidoRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DetalhePedidoMapper {

    /**
     * @param pedido
     * @return
     */
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "codigoCliente", target = "codigoCliente")
    @Mapping(source = "dadosClientes.nome", target = "nome")
    @Mapping(source = "dadosClientes.cpf", target = "cpf")
    @Mapping(source = "dadosClientes.logradouro", target = "logradouro")
    @Mapping(source = "dadosClientes.numero", target = "numero")
    @Mapping(source = "dadosClientes.bairro", target = "bairro")
    @Mapping(source = "dadosClientes.email", target = "email")
    @Mapping(source = "dadosClientes.telefone", target = "telefone")
    @Mapping(source = "dataPedido", target = "dataPedido", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "statusPedido", target = "statusPedido")
    @Mapping(source = "itens", target = "itens")
    DetalhesPedidoRepresentation map(Pedido pedido);
}
