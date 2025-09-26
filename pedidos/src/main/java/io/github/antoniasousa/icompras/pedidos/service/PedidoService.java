package io.github.antoniasousa.icompras.pedidos.service;

import io.github.antoniasousa.icompras.pedidos.exception.ItemNaoEcontradoException;
import io.github.antoniasousa.icompras.pedidos.model.DadosPagamento;
import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.model.StatusPedido;
import io.github.antoniasousa.icompras.pedidos.model.TipoPagamento;
import io.github.antoniasousa.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.antoniasousa.icompras.pedidos.repository.PedidoRepository;
import io.github.antoniasousa.icompras.pedidos.validador.PedidoValidator;
import io.github.antoniasousa.icompras.pedidos.client.ServicoBancarioClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
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
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
    public void ataualizarStatusPagamento(
            Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {

            var pedidoEncontrado = repository
                    .findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

            if (pedidoEncontrado.isEmpty()) {
                var msg = String.format("Pedido não encontrado para o código %d e chave de pagamento %s",
                        codigoPedido, chavePagamento);
                log.error(msg);
                return;
            }

            Pedido pedido = pedidoEncontrado.get();

            if (sucesso) {
                pedido.setStatus(StatusPedido.PAGO);
            }else {
                pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
                pedido.setChavePagamento(observacoes);
            }
            repository.save(pedido);
    }
    @Transactional
    public void adicionarNovoPagamento(
            Long codigoPedido, String dadosCartao, TipoPagamento tipo) {
       var pedidoEncontrado = repository.findById(codigoPedido);

       if (pedidoEncontrado.isEmpty()) {
           throw new ItemNaoEcontradoException("Pedido não encontrado para o código informado.");
       }

       var pedido = pedidoEncontrado.get();
        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo Pagamento realizado, aguardando pagamento");
    }

}
