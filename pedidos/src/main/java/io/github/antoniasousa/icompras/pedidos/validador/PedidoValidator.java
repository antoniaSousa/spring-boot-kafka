package io.github.antoniasousa.icompras.pedidos.validador;

import feign.FeignException;
import io.github.antoniasousa.icompras.pedidos.client.ClientesClient;
import io.github.antoniasousa.icompras.pedidos.client.ProdutoClient;
import io.github.antoniasousa.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.antoniasousa.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.antoniasousa.icompras.pedidos.model.ItemPedido;
import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutoClient produtoClient;
    private final ClientesClient clientesClient;


    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);

    }
    private void validarCliente(Long codigoCliente) {
       try {
           var response = clientesClient.obterDados(codigoCliente);
           ClienteRepresentation cliente = response.getBody();
           log.info("Cliente de codigo: {} encontrado: {}", cliente.codigo(), cliente.nome());
       } catch (FeignException.NotFound e){
           log.error("Cliente não encontrado");
       }
    }
    private void validarItem(ItemPedido item) {
        try {
            var response = produtoClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto de codigo {} encontrado: {}", produto.codigo(), produto.nome());
        }catch (FeignException.NotFound e){
            log.error("Produto não encontrado");
        }
    }
}
