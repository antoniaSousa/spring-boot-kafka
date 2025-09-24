package io.github.antoniasousa.icompras.pedidos.client;

import io.github.antoniasousa.icompras.pedidos.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "produtos", url = "${icompras.pedidos.clients.produtos.url}")
public interface ProdutoClient {

    @GetMapping("{codigo}")
    ResponseEntity<ProdutoRepresentation> obterDados(@RequestParam("codigo") Long codigo);
}
