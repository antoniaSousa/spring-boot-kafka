package io.github.antoniasousa.icompras.pedidos.controller;

import io.github.antoniasousa.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.antoniasousa.icompras.pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;

    public RecebimentoCallbackPagamentoController(PedidoService pedidoService) {

        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO body,
            @RequestHeader(required = true, name = "apiKey") String apiKey
    ) {

        pedidoService.ataualizarStatusPagamento(
                body.codigo(),
                body.chavePagamento(),
                body.status(),
                body.observacoes()
        );

        return ResponseEntity.ok().build();
    }
}
