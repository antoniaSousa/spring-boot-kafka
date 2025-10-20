package io.github.antoniasousa.icompras.pedidos.controller;

import io.github.antoniasousa.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.antoniasousa.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.antoniasousa.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.antoniasousa.icompras.pedidos.exception.ItemNaoEcontradoException;
import io.github.antoniasousa.icompras.pedidos.exception.ValidationException;
import io.github.antoniasousa.icompras.pedidos.model.ErroResposta;
import io.github.antoniasousa.icompras.pedidos.publisher.DetalhePedidoMapper;
import io.github.antoniasousa.icompras.pedidos.publisher.representation.DetalhesPedidoRepresentation;
import io.github.antoniasousa.icompras.pedidos.service.PedidoService;
import jakarta.servlet.http.PushBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto, PushBuilder pushBuilder) {
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok().body(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(
            @RequestBody AdicaoNovoPagamentoDTO dto) {
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dadosCartao(), dto.tipo());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEcontradoException e) {
            var erro = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }

    }
    @GetMapping("{codigo}")
    public ResponseEntity<DetalhesPedidoRepresentation> obterDetalhesPedido(
            @PathVariable("codigo") Long codigo){
        return service
                .carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
