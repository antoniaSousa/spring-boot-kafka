package io.github.antoniasousa.icompras.faturamento;

import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class GeradorNotaFiscalService {

    public void gerar (Pedido pedido){
        log.info("Gerando nota fiscal para o pedido {} " , pedido.getCodigo());
    }
}
