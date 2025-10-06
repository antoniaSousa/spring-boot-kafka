package io.github.antoniasousa.icompras.pedidos.service;

import io.github.antoniasousa.icompras.pedidos.client.ClientesClient;
import io.github.antoniasousa.icompras.pedidos.client.ProdutoClient;
import io.github.antoniasousa.icompras.pedidos.exception.ItemNaoEcontradoException;
import io.github.antoniasousa.icompras.pedidos.model.*;
import io.github.antoniasousa.icompras.pedidos.publisher.PagamentoPublisher;
import io.github.antoniasousa.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.antoniasousa.icompras.pedidos.repository.PedidoRepository;
import io.github.antoniasousa.icompras.pedidos.client.ServicoBancarioClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ServicoBancarioClient servicoBancarioClient;
    private final PagamentoPublisher pagamentoPublisher;
    private final ClientesClient apiClientes;
    private final ProdutoClient apiProdutos;


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

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo ) {
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }
    private void carregarDadosCliente(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        var response = apiClientes.obterDados(codigoCliente);
        pedido.setDadosClientes(response.getBody());

    }
    private void carregarItensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);

    }

    private void carregarDadosProduto(ItemPedido itemPedido) {
        Long codigoPedido = itemPedido.getCodigo();
        var response = apiProdutos.obterDados(codigoPedido);
        itemPedido.setNome(response.getBody().nome());
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
                prepararEPublicarPedidoPago(pedido);
            }else {
                pedido.setStatusPedido(StatusPedido.ERRO_PAGAMENTO);
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

        pedido.setDadosPagamento(pedido.getDadosPagamento());
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo Pagamento realizado, aguardando pagamento");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        repository.save(pedido);
    }
    public void prepararEPublicarPedidoPago(Pedido pedido) {
       pedido.setStatusPedido(StatusPedido.PAGO);
       pedido.setDataPedido(pedido.getDataPedido());
       carregarDadosCliente(pedido);
       carregarItensPedido(pedido);
       pagamentoPublisher.publicar(pedido);
    }







}
