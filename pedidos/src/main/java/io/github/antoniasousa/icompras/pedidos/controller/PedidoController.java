package io.github.antoniasousa.icompras.pedidos.controller;

import io.github.antoniasousa.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.antoniasousa.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.antoniasousa.icompras.pedidos.exception.ValidationException;
import io.github.antoniasousa.icompras.pedidos.model.ErroResposta;
import io.github.antoniasousa.icompras.pedidos.service.PedidoService;
import jakarta.servlet.http.PushBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

   @PostMapping
   public ResponseEntity <Object> criar(@RequestBody NovoPedidoDTO dto, PushBuilder pushBuilder) {
       try {
           var pedido = mapper.map(dto);
           var novoPedido = service.criarPedido(pedido);
           return ResponseEntity.ok().body(novoPedido.getCodigo());
       } catch (ValidationException e) {
           var erro = new ErroResposta("Erro validação", e.getField(), e.getMessage());
           return ResponseEntity.badRequest().body(erro);
       }
   }
//       @PostMapping("pagamentos")
//       public ResponseEntity<Object> adicionarNovoPagamento(
//               @RequestBody AdicionarNovoPagamentoDTO dto){
//                   service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
//                   return ResponseEntity.noContent().build();
//
//       }

    }

